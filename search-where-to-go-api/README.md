# search-where-to-go-api

[Spring-Boot](https://spring.io/projects/spring-boot) Framework로 작성된 API 프로젝트.

## Endpoints

## Endpoints test

### 회원가입
```shell
curl -X POST -H 'Content-Type: application/json' \
    -d '{"username": "$username", "password": "$password"}' \
    "http://localhost:8080/users"
```
* `$username`: 사용자 이름
* `$password`: 비밀번호

회원가입 성공
```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":{"userId":"97ce9dd5-...","username":"test-username","token":"eyJhbGciOiJIUzUxMiJ9...."}}
```
회원가입 실패
```json
{"header":{"isSuccessful":false,"resultCode":2001,"resultMessage":"Validation failed..."},"body":null}
```

### 회원정보 조회
```shell
curl -X GET -H 'Authorization: Token $token' \
    "http://localhost:8080/user"
```
* `$token`: 회원가입 시 응답으로 전달받은 `token` 값

회원정보 조회 성공
```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":{"userId":"97ce9dd5-...","username":"test-username","token":"eyJhbGciOiJIUzUxMiJ9...."}}
```
회원정보 조회 실패
```json
{"header":{"isSuccessful":false,"resultCode":1000,"resultMessage":"user is not logged in."},"body":null}
```

### 로그인
```shell
curl -X POST -H 'Content-Type: application/json' \
    -d '{"username": "$username", "password": "$password"}' \
    "http://localhost:8080/users/login"
```
* `$username`: 사용자 이름
* `$password`: 비밀번호
로그인 성공
```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":{"userId":"97ce9dd5-...","username":"test-username","token":"eyJhbGciOiJIUzUxMiJ9...."}}
```
로그인 실패
```json
{"header":{"isSuccessful":false,"resultCode":1001,"resultMessage":"user is not exists."},"body":null}
```

### 키워드 검색
```shell
curl -X GET -H 'Authorization: Token $token' \
    "http://localhost:8080/v1.0/places?query=$query"
```
* `$token`: 로그인 시 응답으로 전달받은 `token` 값
* `$query`: 검색 키워드

키워드 검색 성공
```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":[{"name":"스타벅스 춘천구봉산R","...":"..."}]}
```
키워드 검색 실패
```json
{"header":{"isSuccessful":false,"resultCode":3000,"resultMessage":"external api error."},"body":null}
```
```json
{"header":{"isSuccessful":false,"resultCode":2001,"resultMessage":"Request 'query' is invalid in value with ''"},"body":null}
```

### 내 검색 히스토리 
```shell
curl -X GET -H 'Authorization: Token $token' \
    "http://localhost:8080/v1.0/places/histories?pageNum=$pageNum&pageSize=$pageSize"
```
* `$token`: 로그인 시 응답으로 전달받은 `token` 값
* `$pageNum`: 현재 페이지 번호 (default 0)
* `$pageSize`: 페이지 사이즈 (default 10)

검색 히스토리 조회 성공
```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":[{"historyId":2,"query":"starbucks","userId":"7ef78c01-...","createDatetime":"2021-03-22T00:24:30.056361"},{"historyId":1,"query":"starbuks","userId":"7ef78c01-a232-4eb0-bc7f-60a88b372d08","createDatetime":"2021-03-22T00:24:25.168301"}]}
```
검색 히스토리 조회 실패
```json
{"header":{"isSuccessful":false,"resultCode":2001,"resultMessage":"Request 'pageNum' is invalid in value with '-1'Request 'pageSize' is invalid in value with '0'"},"body":null}
```

### 인기 키워드 목록 조회
```shell
curl -X GET -H 'Authorization: Token $token' \
    "http://localhost:8080/v1.0/places/favorites"
```
* `$token`: 로그인 시 응답으로 전달받은 `token` 값

인기 키워드 목록 조회 성공
```json
{"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":[{"query":"해장국","count":324},{"query":"삼겹살","count":234},{"query":"카카오뱅크","count":234},{"query":"곱창","count":233},{"query":"카페","count":203},{"query":"전골","count":131},{"query":"국밥","count":120},{"query":"돈까쓰","count":103},{"query":"코이라멘","count":103},{"query":"카카오","count":52}]}
```
인기 키워드 목록 조회 실패
```json
{"header":{"isSuccessful":false,"resultCode":3000,"resultMessage":"external api error."},"body":null}
```

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

### [ehcache](https://www.ehcache.org/)

> JAVA’S MOST WIDELY-USED CACHE

Open API 조회 시, 캐시를 적용하고 관리할 CacheManager 로써 사용

#### Gradle
```groovy
implementation 'net.sf.ehcache:ehcache:2.10.6'
```
