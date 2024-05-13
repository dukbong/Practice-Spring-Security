package com.example.securitytest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.example.securitytest.handler.CustomAccessDeniedHandler;
import com.example.securitytest.handler.CustomAuthenticationEntryPoint;
import com.example.securitytest.handler.CustomLogoutSuccessHandler;
import com.example.securitytest.handler.CustomSessionExpiredStrategy;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final MessageSource messageSource;
	
    @Value("${custom.security.role-hierarchy}")
    private String roleHierarchy;
    
	// session 만료 시 발생
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionExpiredStrategy();
    }
	
	// Session 이벤트 모니터링
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
    // 권한 핸들러
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(messageSource);
    }
    
    // 인증 핸들러
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint(messageSource);
    }
	
	// SessionRegistry를 사용하여 현재 활성화된 세션을 추적 ( 세션 정보도 얻을 수 있다. )
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
    // 권한 계층화
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy(roleHierarchy);
		return roleHierarchyImpl;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 필터를 사용해서 contextHolder가 유지되는지 확인하기 위한 테스트필터
		// http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(auth -> {
				auth.requestMatchers("/", "/login/**", "/join").permitAll();
				// auth.requestMatchers("/info/**").hasAnyRole("A", "B", "C");
				// auth.requestMatchers("/taking").hasAnyRole("B","C");
				
				// RoleHierarchy를 Bean 등록하면 이런식으로 줄여서 사용이 가능하다.
				// 장점 : 가독성에 좋다.
				auth.requestMatchers("/info").hasAnyRole("A");
				auth.requestMatchers("/get/allsession").hasRole("B");
				auth.requestMatchers("/delete/session/c").hasRole("B");
				auth.requestMatchers("/admin").hasRole("C");
				auth.anyRequest().authenticated();
			});
		
		http.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler())
									   .authenticationEntryPoint(authenticationEntryPoint()));
		
		http.formLogin(form -> form.disable());
		
		http.logout(logout -> logout.logoutUrl("/logout")
									.invalidateHttpSession(true)
									.deleteCookies("JSESSIONID")
									.logoutSuccessHandler(new CustomLogoutSuccessHandler()));
		
		http.csrf(csrf -> csrf.disable());
		
		// csrf 토큰을 쿠키로 전달할것이고 /login/**, /join, /에는 csrf토큰이 없어도 된다는 설정이다.
		// withHttpOnlyFalse()를 설정하면 Http에서 쿠키로 해당 토큰을 볼 수 있게 되는데 이는 보안상 좋지 못하기 때문에 배포시 설정하지 않는다.
		// http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/login/**", "/join", "/"));
		
		// 세션 생성 정책
		// - 기본적으로 토큰 인증 기반과 RESTful API 환경에서는 STATELESS를 선택한다.
		// - IF_REQUIRED : 필요한 경우에만 세션을 생성
		// - ALWAYS : 모든 요청에 대해 새로운 세션 생성 [오버헤드 증가..]
		// - NEVER : 절대 세션을 생성하지 않겠다.
		// - STATELESS : 세션을 비활성화 하겠다.
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		
		
		
		// 중복 로그인을 처리하는 sessionManagement
		// maximumSession(n) -> 몇개까지 한번에 로그인 할것인가?
		// maxSessionsPreventsLogin -> 만약 maximumSession 갯수 초과 후 로그인 시도시 어떻게 막을것인가?
		//							   [true  : 새롭게 로그인 시도한 것을 막겠다.]
		//							   [false : 기존 로그인 중 하나를 취소시키고 새로운 로그인을 통과시킨다. ]
		http.sessionManagement(session -> session.maximumSessions(1)
												 .maxSessionsPreventsLogin(true)
												 // sessionRegistry 관리
												 .sessionRegistry(sessionRegistry())
												 // 세션 만료시 처리
												 .expiredSessionStrategy(sessionInformationExpiredStrategy()));
		 
		// 세션 고정 보호
		// 세션 고정은 웹의 보안 취약점 중 하나이며 특정 세션 ID를 훔치거나 해킹하는 공격
		// spring-security에서는 sessionManagement에서 sessionFixation()를 통해 이를 보호할 방법을 설정할 수 있다.
		//
		// 1. none : 로그인시 세션 정보 변경하지 않음
		// 2. newSession() : 로그인시 세션 새로 생성
		// 3. changeSessionId() : 로그인시 동일한 세션에 대한 Id 변경 [🎉]
		http.sessionManagement(session -> session.sessionFixation(sessionFixation -> sessionFixation.changeSessionId()));
		
		// 인증 성공 후 세션이 생성되거나 변경될 때 SessionRegistry에 세션 정보를 등록합니다. [모르겠음]
//		http.sessionManagement(session -> session.sessionAuthenticationStrategy(sessionAuthenticationStrategy()));
		
		// 세션 만료시 리다이렉트할 페이지 설정
		// http.sessionManagement(session -> session.invalidSessionUrl("/"));
		
		return http.build();
	}
	
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
//    }
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
