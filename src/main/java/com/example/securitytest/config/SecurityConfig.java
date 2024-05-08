package com.example.securitytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.securitytest.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("C > B > A");
		return roleHierarchyImpl;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 필터를 사용해서 contextHolder가 유지되는지 확인하기 위한 테스트필터
		// http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(auth -> {
				auth.requestMatchers("/", "/login", "/join").permitAll();
				// auth.requestMatchers("/info/**").hasAnyRole("A", "B", "C");
				// auth.requestMatchers("/taking").hasAnyRole("B","C");
				
				// RoleHierarchy를 Bean 등록하면 이런식으로 줄여서 사용이 가능하다.
				// 장점 : 가독성에 좋다.
				auth.requestMatchers("/info/**").hasAnyRole("A");
				auth.requestMatchers("/taking").hasAnyRole("B");
				auth.requestMatchers("/admin").hasRole("C");
				auth.anyRequest().authenticated();
			});
		
		http.formLogin(form -> form.disable());
		
		http.csrf(csrf -> csrf.disable());
		
		// 중복 로그인을 처리하는 sessionManagement
		// maximumSession(n) -> 몇개까지 한번에 로그인 할것인가?
		// maxSessionsPreventsLogin -> 만약 maximumSession 갯수 초과 후 로그인 시도시 어떻게 막을것인가?
		//							   [true  : 새롭게 로그인 시도한 것을 막겠다.]
		//							   [false : 기존 로그인 중 하나를 취소시키고 새로운 로그인을 통과시킨다. ]
		http.sessionManagement(session -> session.maximumSessions(1)
												 .maxSessionsPreventsLogin(true));
		
		// 세션 고정 보호
		// 세션 고정은 웹의 보안 취약점 중 하나이며 특정 세션 ID를 훔치거나 해킹하는 공격
		// spring-security에서는 sessionManagement에서 sessionFixation()를 통해 이를 보호할 방법을 설정할 수 있다.
		//
		// 1. none : 로그인시 세션 정보 변경하지 않음
		// 2. newSession() : 로그인시 세션 새로 생성
		// 3. changeSessionId() : 로그인시 동일한 세션에 대한 Id 변경 [🎉]
		http.sessionManagement(session -> session.sessionFixation(sessionFixation -> sessionFixation.changeSessionId()));
		
		return http.build();
	}
	
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
