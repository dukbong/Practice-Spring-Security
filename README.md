# Practice-Spring-Security

##### 사용 기술 : JPA, Spring-Security
##### 사용 디비 : H2 Database

---

### 세션 기반 인증 - 구현 (O)
- 검증 방식 커스텀 : `loadUserByUsername` -> `loadUserByUsernameAndUrl`
  - `UserDetailsService` 커스텀
- `AuthenticationManager` 커스텀 -> `AuthenticationProvider` 구현

### 토큰 기반 인증 - 구현 (X)
