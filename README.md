# 스프링 시큐리티

## 프로젝트 종속성
- Spring Boot DevTools
- Lombok
- Spring Web
- Thymeleaf
- Spring Security
- Spring Data JPA
![프로젝트종속성](https://user-images.githubusercontent.com/97961558/184789108-f5821d28-33ac-4808-a8a8-b89d0cbe7511.jpg)

## 라이브러리를 추가시키기만 해도 다음과 같은 로그인 화면이나옴
- 로그에 비밀번호가 나온다
![초반로그](https://user-images.githubusercontent.com/97961558/184790512-71dd5e22-d0a3-4ace-85b0-d86dd08c8752.jpg)
- 로그인 화면
![로그인화면](https://user-images.githubusercontent.com/97961558/184790665-aa86c34b-2b86-434e-a585-0911ff288700.jpg)
- 로그인 성공시 (아이디 : user, 비밀번호 : 로그에나옴)
![로그인성공](https://user-images.githubusercontent.com/97961558/184790668-99106188-98a4-449d-93ce-1b672abcacb2.jpg)
- 로그인 실패 시
![로그인실패](https://user-images.githubusercontent.com/97961558/184790660-cf835092-7086-4d7b-bd65-4bc01c5e3e11.jpg)


## 패스워드 인코더
- 패스워드가 뚫릴 수도 있기 때문에 패스워드를 인코딩하여 저장한다
- 인코딩된 패스워드가 로그에 나온 모습
![비밀번호인코딩](https://user-images.githubusercontent.com/97961558/184790962-a2b936a9-7405-41e9-a9f9-a2c55185bf8e.jpg)

## 인증
- 아이디와 패스워드를 보고 사용자가 맞는지 확인하는 과정

## 인가
- 인증된 사용자가 어떤 기능에 대해 권한이 있는지에 대한 여부
 ### 어노테이션만으로 접근제한을 걸기 위해 추가할 것들
  - @EnableGlobalMethodSecurity의 적용
    -시큐리티 관련 설정 클래스에 어노테이션 추가
  - 접근 제한이 필요한 컨트롤러의 메서드에 @PreAuthorize적용
    - @PreAuthorize("hasRole('ADMIN')") 어드민 권한자만
    - @PreAuthorize("permitAll()") 모두다 가능
 
## 로그인에 성공해 All 페이지가 나온 모습
 ![로그인인증인가](https://user-images.githubusercontent.com/97961558/184792825-5b1d8343-90e7-4833-8332-f1a3001906e5.jpg)
 
## 구글 프로젝트와 연결
https://deserted-thought-336.notion.site/11-1-1-018e19893ce14b778f10f463ace36db1

## 구글 로그인이 생긴 것
- 라이브러리 추가(build.gradle)
- application-properties에 추가
![구글로그인생성](https://user-images.githubusercontent.com/97961558/184793602-a24de867-4f11-4aff-80bf-a39120ea78b1.jpg)

## 구글 최초 로그인시 자동 회원가입
### 문제점
- 모든 패스워드가 1111로만 처리
- 사용자의 이메일 외에도 이름을 닉네임처럼 사용할 수 없음
### 해결점
- fromSocial이라는 속성값을 이용하여 폼 방식의 로그인과 소셜 로그인을 분리함

## 소셜 최초 로그인시 수정화면으로 넘어감
![소셜로그인수정화면전환](https://user-images.githubusercontent.com/97961558/184794052-14516bf1-674d-4a3c-8e0c-5cefd0fe3517.jpg)

## 로그인 정보를 쿠키로 넘겨서 브라우저를 닫았다 열어도 로그인상태 유지
![쿠키로로그인저장](https://user-images.githubusercontent.com/97961558/184794202-0782d0fb-b736-476a-b5e7-987e518dff37.jpg)

## 특정 필터를 특정
-ApiCheckFilter가 UsernamePasswordAuthenticationFilter이전에 동작하도록 지정
  -http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
 
## SecurityConfig
- 문자열로 패턴을 입력받는 생성자 추가로 수정
- return new ApiCheckFilter("/notes/**/*");에 괄호안 추가
![문자열로패턴입력받기](https://user-images.githubusercontent.com/97961558/184795297-e02b5f3a-1187-45c9-9007-22119db0bbaa.jpg)

## JWT
- Header와 Payload를 단순히 Base64로 인코딩
    - 누군가 디코딩을 하면 내용물을 알 수 있어서 문제가 생김
- 마지막에 Signature을 이용해서 암호화된 값을 같이 사용
### JWT문자열의 구성
- Header = 토큰 타입과 알고리즘을 의미
- Payload - 이름과 값의 쌍을 Claim이라고 하고, claims를 모아둔 객체를 의미
- Signature 헤더의 인코딩 값과 정보의 인코딩 값을 합쳐 비밀 키로 해시 함수로 처리된 결과

### JWT설정
- JWT라이브러리 추가
- JWTUtil
  - 스프링 환경이 아닌 곳에서 사용할 수 있도록 간단한 유틸리티 클래스로 설계
  - generateToken()은 JWT토큰을 생성하는 역할
  - validateAndExtract()는 인코딩된 문자열에서 원하는 값을 추출하는 용도
  - generateToken()의 경우 JWT문자열 자체를 알면 누구든 API를 사용할 수 있다는 문제가 생기므로 만료 기간값을 설정(30일)
  - validateAndExtract()는 JWT문자열을 검증하는 역할
  ### 필터에 적용된 모습
![JWT](https://user-images.githubusercontent.com/97961558/184796453-cc5aa334-8710-465e-9c41-1ec7a545f35e.jpg)

