# Practice-Spring-Security

##### 사용 기술 : JPA, Spring-Security
##### 사용 디비 : H2 Database

---

### 세션 기반 인증 - 구현 목록
- Provider 커스텀(AuthenticationManagement)
- 인증 방식 커스텀 & 사용자 인증 토큰 커스텀
- Security 세션 이벤트 추적
  - 세션 생성 및 파기 이벤트 추척 모니터링
- 로그인 이벤트 추적
  - 로그인 성공 및 실패 추적 모니터링
- 세션 고정 공격 방지
- 중복 로그인 설정
- 세션 개수 및 만료시간 설정
  - 세션 만료시 핸들러 조작
- 세션 정보 추적 및 관리
  - 활성화 세션 정보 및 세션 조작 
- 계층 권한 설정
- 세션 생성 정책 설정
- 미인증 & 미인가 접근 핸들러 조작

#### formLogin 설정 시 흐름
- `loginProcessingUrl`을 등록하면 Spring security가 내부적으로 인증을 처리한다.
- login form 제출시 loginProcessingUrl로 POST 방식으로 보내지게 됩니다.
  - FormLoginConfigurer.java - `createLoginProcessingUrlMatcher()`
- UsernamePasswordAuthenticationFilter는 이 요청을 가지고 usernamePasswordAUthenticationToken 객체를 생성합니다.
  - UsernamePasswordAuthenticationFilter.java - `attemptAuthentication()`
- 등록된 AuthenticationProvider를 모두 순회하며 `Authentication: authenticate()`를 호출합니다.
  - 이 과정에서 `boolean: supports()`를 통해 처리가 가능한지 불가능한지 알 수 있습니다.
- 만약 인증에 성공한다면 멈추고 Authentication 객체를 SecurityContextHolder에 저장하고 로그인 처리를 하게 됩니다.
