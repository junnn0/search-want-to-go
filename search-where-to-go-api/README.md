# search-where-to-go-api

[Spring-Boot](https://spring.io/projects/spring-boot) Framework로 작성된 API 프로젝트.

## Endpoints

## Test


## DB

### [H2](https://www.h2database.com/)

In-memory 데이터베이스로 사용.

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:search-where-to-go
    driver-class-name: org.h2.Driver
    username: sa
    password:
```

## 의존성

### [jjwt](https://github.com/jwtk/jjwt)

> Java JWT: JSON Web Token for Java and Android

JSON Web Token을 사용하여 사용자의 로그인 정보를 생성하고 인증하기 위하여 사용.

#### Gradle
```groovy
implementation 'io.jsonwebtoken:jjwt:0.9.1'
```

### [PBKDF2](https://github.com/m9aertner/PBKDF2)

> A free Java implementation of RFC 2898 / PKCS#5 PBKDF2

회원가입한 사용자의 비밀번호를 PBKDF2 암호화 알고리즘을 통해 암호화하기 위하여 사용.

#### Gradle
```groovy
implementation 'de.rtner:PBKDF2:1.1.4'
```

### [javafaker](https://github.com/DiUS/java-faker)

> This library is a port of Ruby's faker gem (as well as Perl's Data::Faker library) that generates fake data. It's useful when you're developing a new project and need some pretty data for showcase.

테스트 코드에서 임의의 중복되지 않는 데이터를 생성하기 위하여 사용.

#### Gradle
```groovy
testImplementation 'com.github.javafaker:javafaker:1.0.2'
```

