# search-where-to-go-api

[Spring-Boot](https://spring.io/projects/spring-boot) Framework로 작성된 API 프로젝트.

## Endpoints

### 회원가입

```shell
curl -X POST -H 'Content-Type: application/json' \
    -d '{"username": "$username", "password": "$password"}' \
    "http://localhost:8080/users"
```

* username: 사용자 이름
* password: 비밀번호

#### 회원가입 성공

```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":{"userId":"97ce9dd5-150c-4444-9141-cd1ba6188019","username":"test-username","token":"eyJhbGciOiJIUzUxMiJ9...."}}
```
#### 회원가입 실패

```json
{"header":{"isSuccessful":false,"resultCode":2001,"resultMessage":"Validation failed..."},"body":null}
```

### 회원정보 조회

```shell
curl -X GET -H 'Content-Type: application/json' \
    -H 'Authorization: Token $token'
    "http://localhost:8080/user"
```

* token: 회원가입 시 응답으로 전달받은 `token` 값

#### 회원정보 조회 성공

```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":{"userId":"97ce9dd5-150c-4444-9141-cd1ba6188019","username":"test-username","token":"eyJhbGciOiJIUzUxMiJ9...."}}
```

#### 회원정보 조회 실패

```json
{"header":{"isSuccessful":false,"resultCode":1000,"resultMessage":"user is not logged in."},"body":null}
```

### 로그인

```shell
curl -X POST -H 'Content-Type: application/json' \
    -d '{"username": "$username", "password": "$password"}'
    "http://localhost:8080/users/login"
```

#### 로그인 성공

```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":{"userId":"97ce9dd5-150c-4444-9141-cd1ba6188019","username":"test-username","token":"eyJhbGciOiJIUzUxMiJ9...."}}
```

#### 로그인 실패

```json
{"header":{"isSuccessful":false,"resultCode":1001,"resultMessage":"user is not exists."},"body":null}
```

### 검색 관련

키워드 검색 -> 내 검색 히스토리 -> 인기 키워드 목록




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
