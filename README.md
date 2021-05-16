# SpringSecurityJwtMysql
# 파일 구조
![4](https://user-images.githubusercontent.com/34269126/74104558-4fda2f80-4b99-11ea-92b5-038e391633e5.PNG)

# 소개 ( https://jwt.io )
jwt.io가 제공하는 멋진 소개가 있습니다! 참고 하세요!

### **JSON 웹 토큰이란 무엇입니까?**
JWT (JSON Web Token)는 개방형 표준 (RFC 7519)으로, 당사자간에 정보를 안전하게 JSON 객체로 전송하기위한 간결하고 독립적 인 방법을 정의합니다. 이 정보는 디지털 서명되어 있으므로 확인하고 신뢰할 수 있습니다. JWT는 비밀 (HMAC 알고리즘 사용) 또는 RSA를 사용하는 공개 / 개인 키 쌍을 사용하여 서명 할 수 있습니다.

이 정의의 몇 가지 개념을 더 자세히 설명하겠습니다.

컴팩트 : JWT는 크기가 작기 때문에 URL, POST 매개 변수 또는 HTTP 헤더 내부를 통해 보낼 수 있습니다. 또한 크기가 작을수록 전송 속도가 빠릅니다.

자체 포함 : 페이로드에는 사용자에 대한 모든 필수 정보가 포함되어 있으므로 데이터베이스를 두 번 이상 쿼리 할 필요가 없습니다.

### **언제 JSON 웹 토큰을 사용해야합니까?**
JSON 웹 토큰이 유용한 시나리오는 다음과 같습니다.

인증 : 이것은 JWT를 사용하는 가장 일반적인 시나리오입니다. 사용자가 로그인하면 이후의 각 요청에는 JWT가 포함되어 사용자가 해당 토큰으로 허용되는 경로, 서비스 및 리소스에 액세스 할 수 있습니다. 싱글 사인온 (SSO)은 작은 오버 헤드와 다른 도메인에서 쉽게 사용할 수 있기 때문에 오늘날 JWT를 널리 사용하는 기능입니다.

정보 교환 : JSON 웹 토큰은 당사자간에 정보를 안전하게 전송하는 좋은 방법입니다. 공개 / 개인 키 쌍을 사용하여 JWT에 서명 할 수 있기 때문에 발신자가 자신이 누구인지 확인할 수 있습니다. 또한 서명은 헤더와 페이로드를 사용하여 계산되므로 내용이 변경되지 않았는지 확인할 수도 있습니다.

### **JSON 웹 토큰 구조는 무엇입니까?**
JSON 웹 토큰은 다음과 같이 점 (.)으로 구분 된 세 부분으로 구성됩니다 .

 1. 머리글 
 2. 유효 탑재량
 3.  서명

따라서 JWT는 일반적으로 다음과 같습니다.

`xxxxx`. `yyyyy`.`zzzzz`

다른 부분을 나누자.

**머리글**

헤더는 일반적으로 JWT 인 토큰 유형과 HMAC SHA256 또는 RSA와 같은 사용되는 해싱 알고리즘의 두 부분으로 구성됩니다.

예를 들면 다음과 같습니다.

    {
       " alg " : " HS256 " ,
       " typ " : " JWT " 
    } 

그런 다음이 JSON은 Base64Url로 인코딩되어 JWT의 첫 번째 부분을 형성합니다.

**유효 탑재량**

토큰의 두 번째 부분은 클레임이 포함 된 페이로드입니다. 클레임은 엔터티 (일반적으로 사용자) 및 추가 메타 데이터에 대한 진술입니다. 클레임에는 예약, 공개 및 개인 클레임의 세 가지 유형이 있습니다.

+ 예약 된 클레임 : 이들은 유용하고 상호 운용 가능한 클레임 집합을 제공하기 위해 필수는 아니지만 권장되는 미리 정의 된 클레임 집합입니다. 그 중 일부는 iss (발급자), exp (만료 시간), sub (제목), aud (청중) 및 기타입니다.
JWT가 간결해야하므로 클레임 이름은 3 자에 불과합니다.

+ 공개 주장 : 이들은 JWT를 사용하는 사람들에 의해 마음대로 정의 될 수 있습니다. 그러나 충돌을 피하려면 IANA JSON 웹 토큰 레지스트리에서 정의하거나 충돌 방지 네임 스페이스가 포함 된 URI로 정의해야합니다.

+ 비공개 소유권 주장 : 소유권 주장에 동의 한 당사자간에 정보를 공유하기 위해 생성 된 맞춤 소유권 주장입니다.

페이로드의 예는 다음과 같습니다.

    {
       " sub " : " 1234567890 " ,
       " name " : " John Doe " ,
       " admin " : true 
    }

그런 다음 페이로드는 Base64Url로 인코딩되어 JSON 웹 토큰의 두 번째 부분을 형성합니다.

***서명***

서명 부분을 만들려면 인코딩 된 헤더, 인코딩 된 페이로드, 비밀, 헤더에 지정된 알고리즘을 가져 와서 서명해야합니다.

예를 들어, HMAC SHA256 알고리즘을 사용하려는 경우 다음과 같은 방식으로 서명이 작성됩니다.

    HMACSHA256(
      base64UrlEncode(header) + "." +
      base64UrlEncode(payload),
      secret)

서명은 JWT의 발신자가 본인인지 확인하고 메시지가 변경되지 않았 음을 확인하는 데 사용됩니다. 모두 모아

출력은 HTML과 HTTP 환경에서 쉽게 전달할 수있는 점으로 구분 된 3 개의 Base64 문자열이며 SAML과 같은 XML 기반 표준과 비교할 때 더 간결합니다.

다음은 이전 헤더와 페이로드가 인코딩 된 JWT를 나타내며 비밀로 서명됩니다. 인코딩 된 JWT

![enter image description here](https://camo.githubusercontent.com/a56953523c443d6a97204adc5e39b4b8c195b453/68747470733a2f2f63646e2e61757468302e636f6d2f636f6e74656e742f6a77742f656e636f6465642d6a7774332e706e67)


### **JSON 웹 토큰은 어떻게 작동합니까?**

인증시 사용자가 자격 증명을 사용하여 성공적으로 로그인하면 JSON 웹 토큰이 반환되고 기존의 세션 생성 방식 대신 로컬 (일반적으로 로컬 저장소에 쿠키가 사용될 수 있음)로 저장되어야합니다. 서버와 쿠키를 반환합니다.

사용자가 보호 된 라우트 또는 자원에 액세스하려고 할 때마다 사용자 에이전트는 일반적으로 Bearer 스키마를 사용하여 Authorization 헤더에서 JWT를 보내야합니다. 헤더의 내용은 다음과 같아야합니다.

    Authorization: Bearer <token>

이것은 사용자 상태가 서버 메모리에 저장되지 않기 때문에 상태 비 저장 인증 메커니즘입니다. 서버의 보호 된 라우트는 Authorization 헤더에서 유효한 JWT를 확인하고, 존재하는 경우 사용자는 보호 된 자원에 액세스 할 수 있습니다. JWT가 자체적으로 포함되어 있으므로 필요한 모든 정보가 있으므로 데이터베이스를 여러 번 쿼리해야 할 필요성이 줄어 듭니다.

이를 통해 상태 비 저장 상태의 데이터 API에 전적으로 의존하고 다운 스트림 서비스에 대한 요청을 할 수 있습니다. API를 제공하는 도메인은 중요하지 않으므로 쿠키를 사용하지 않으므로 CORS (Cross-Origin Resource Sharing)는 문제가되지 않습니다.

다음 다이어그램은이 프로세스를 보여줍니다.

![enter image description here](https://camo.githubusercontent.com/5871e9f0234542cd89bab9b9c100b20c9eb5b789/68747470733a2f2f63646e2e61757468302e636f6d2f636f6e74656e742f6a77742f6a77742d6469616772616d2e706e67)


### **JWT 인증 요약**
토큰 기반 인증 스키마는 세션 / 쿠키와 비교할 때 중요한 이점을 제공하기 때문에 최근에 크게 인기를 얻었습니다.

+ CORS
+ CSRF 보호 불필요
+ 모바일과 더 나은 통합
+ 권한 부여 서버의 부하 감소
+ 분산 세션 저장소가 필요하지 않습니다

이 접근 방식으로 일부 절충이 이루어져야합니다.

+ XSS 공격에 더 취약
+ 액세스 토큰에 오래된 권한 주장이 포함될 수 있습니다 (예 : 일부 사용자 권한이 취소 된 경우)
+ 클레임 수가 증가하면 액세스 토큰 크기가 커질 수 있습니다.
+ 파일 다운로드 API는 구현하기 까다로울 수 있습니다
+ 진정한 무국적과 취소는 상호 배타적입니다

##### **JWT 인증 흐름은 매우 간단합니다** 

사용자는 권한 부여 서버에 신임 정보를 제공하여 새로 고침 및 액세스 토큰을 얻습니다.
사용자는 보호 된 API 리소스에 액세스하기 위해 각 요청과 함께 액세스 토큰을 보냅니다.
액세스 토큰은 서명되었으며 사용자 ID (예 : 사용자 ID) 및 권한 부여 청구를 포함합니다.
인증 클레임이 액세스 토큰에 포함된다는 점에 유의해야합니다. 이것이 왜 중요한가? 자, 권한 토큰 (예 : 데이터베이스의 사용자 권한)이 액세스 토큰 수명 동안 변경되었다고 가정 해 봅시다. 이러한 변경 사항은 새 액세스 토큰이 발급 될 때까지 적용되지 않습니다. 대부분의 경우 액세스 토큰의 수명이 짧기 때문에 큰 문제는 아닙니다. 그렇지 않으면 불투명 토큰 패턴으로 이동하십시오.

### **구현 세부 사항**
Java와 Spring을 사용하여 JWT 토큰 기반 인증을 구현하는 방법과 Spring 보안 기본 동작을 재사용하는 방법을 살펴 보겠습니다. Spring Security 프레임 워크는 세션 쿠키, HTTP 기본 및 HTTP 다이제스트와 같은 인증 메커니즘을 이미 처리하는 플러그인 클래스와 함께 제공됩니다. 그럼에도 불구하고 JWT에 대한 기본 지원이 부족하여 작동하도록 손을 더럽힐 필요가 있습니다.

### **MySQL DB**
이 데모는 현재 Spring Boot에 의해 자동으로 구성된 auth 라는 MySQL 데이터베이스를 사용하고 있습니다. 다른 데이터베이스에 연결 application.properties 하려면 리소스 디렉토리 내의 파일 에서 연결을 지정해야합니다 . 참고 hibernate.hbm2ddl.auto=create-drop드롭하고 깨끗한 데이터베이스 우리가 (당신이 실제 프로젝트에서이를 사용하는 경우이를 변경할 수 있습니다)를 배포 할 때마다 생성됩니다. 프로젝트의 예는 다음과 같습니다.
  
 
    # App Properties       
    spring.datasource.url=jdbc:mysql://localhost:3306
    /authserverTimezone=UTC&characterEncoding=UTF-8                                                   
    spring.datasource.username=root                                                              
    spring.datasource.password=root                                                                     
    spring.jpa.generate-ddl=true
                                                                                      
    #JWT                                                         
    suhyun.app.jwtSecret=jwtSecretKey                                                                   
    suhyun.app.jwtExpiration=86400
    
    #Port
    server.port=9966
    
   
 ### **핵심코드**
 
 1. `List ite`
 2. `JwtTokenFilter`
 3. `JwtTokenFilterConfigurer`
 4. `JwtTokenProvider`
 5. `MyUserDetails`
 6. `WebSecurityConfig`
 7. `JwtTokenFilter`
 
 ### **JwtTokenFilter**

`JwtTokenFilter` 필터는 각각의 API (인가된다 `/**` 로그인의 토큰 끝점의 예외 (포함) `/users/signin`)과 끝점 singup ( `/users/signup`).

이 필터는 다음과 같은 책임이 있습니다.

1. 인증 헤더에서 액세스 토큰을 확인하십시오. 헤더에 액세스 토큰이 있으면 인증을 위임하여 `JwtTokenProvider` 인증 예외를 발생 시킵니다.
2. JwtTokenProvider가 수행 한 인증 프로세스 결과를 기반으로 성공 또는 실패 전략을 호출합니다.

`chain.doFilter(request, response)`인증에 성공하면 호출 하십시오 . 마지막 필터 중 하나 인 FilterSecurityInterceptor # doFilter 는 요청 된 API 자원을 처리하는 컨트롤러에서 실제로 메소드를 호출해야 하므로 요청 처리를 다음 필터로 진행하려고합니다 .

    String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
    if (token != null && jwtTokenProvider.validateToken(token)) {
      Authentication auth = jwtTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(req, res);

### **JwtTokenFilterConfigurer**

스프링 부트 보안 `JwtTokenFilter` 을에 추가합니다 `DefaultSecurityFilterChain`.

    JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider); http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);


### **JwtTokenProvider**

`JwtTokenProvider` 다음과 같은 책임이있다 :

1.액세스 토큰 서명 확인
2.Access 토큰에서 ID 및 권한 부여 클레임을 추출하고이를 사용하여 UserContext를 작성하십시오.
3.액세스 토큰이 잘못되었거나 만료되었거나 단순히 적절한 서명 키로 토큰에 서명되지 않은 경우 인증 예외가 발생합니다.

### **MyUserDetails**

`UserDetailsService` 자체 사용자 정의 loadUserbyUsername 함수 를 정의하기 위해 구현 합니다. `UserDetailsService`인터페이스는 사용자 관련 데이터를 검색하기 위해 사용된다. 여기에는 사용자 이름을 기반으로 사용자 엔티티를 찾고 loadUserByUsername 이라는 하나의 메소드 가 있으며 사용자 찾기 프로세스를 사용자 정의하기 위해 대체 할 수 있습니다.

`DaoAuthenticationProvider`인증 중에 사용자에 대한 세부 사항을로드 하는 데 사용됩니다 .

### **WebSecurityConfig**

이 `WebSecurityConfig`클래스 `WebSecurityConfigurerAdapter`는 사용자 지정 보안 구성을 제공하도록 확장 됩니다.

이 클래스에서 다음 Bean이 구성되고 인스턴스화됩니다.

1. `JwtTokenFilter`
2. `PasswordEncoder`

또한 `WebSecurityConfig#configure(HttpSecurity http)`메소드 내부에서 보호 / 비보호 API 엔드 포인트를 정의하는 패턴을 구성합니다. 쿠키를 사용하지 않기 때문에 CSRF 보호를 비활성화했습니다.

  `@Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers("/api/auth/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }`
                                                                                                                                                    
Check database tables ->
![1](https://user-images.githubusercontent.com/34269126/74102353-29f75f80-4b86-11ea-90c0-c187530bc81d.PNG)

– Insert data to roles table ->
* INSERT INTO roles(name) VALUES('ROLE_USER');
* INSERT INTO roles(name) VALUES('ROLE_PM');
* INSERT INTO roles(name) VALUES('ROLE_ADMIN');

![2](https://user-images.githubusercontent.com/34269126/74102356-2bc12300-4b86-11ea-8ab6-ef3c008687b0.PNG)

### **SignUp**
+ Umsu Role_User and Role_Admin
+ SuHyuk Role_Pm and Role_Admin

![5](https://user-images.githubusercontent.com/34269126/74105071-03452300-4b9e-11ea-9b07-9e8e9d3c5090.PNG)

### **Authentication**
![6](https://user-images.githubusercontent.com/34269126/74105072-04765000-4b9e-11ea-9b97-c6d3df7cf163.PNG)

### **Authorization(role)**
![7](https://user-images.githubusercontent.com/34269126/74105073-06401380-4b9e-11ea-82a8-a8139a16043e.PNG)


