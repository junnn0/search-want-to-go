# search-where-to-go-api

[Spring-Boot](https://spring.io/projects/spring-boot) Framework로 작성된 API 프로젝트입니다.

## 추가 고려사항

### Endpoint에 부하가 매우 커졌을 경우

### 동시성 문제

인기 키워드 조회 수 변경 시 DB의 부하를 최대한 줄이고 정확성을 제공하기 위해서 캐시를 사용합니다.<br/>
검색 시 특정 키워드에 대한 조회수를, 메모리 내의 캐시역할을 하는 Map에서 관리하고 주기적으로 해당 캐시에 누산된 값을 DB에 업데이트합니다.<br/>
인기 키워드 조회 시에는 DB에서 가져온 조회수와 캐시 내의 존재하는 조회수를 합하여 응답합니다.<br/>

### 확장성

현재 캐시는 로컬에서 Map을 사용하고 있지만, 이후 부하가 커지는 경우 외부 In-memory db인 redis를 구축하고<br/>
Redis를 사용하도록 SearchCounter 인터페이스를 구현한 Bean을 생성하여 사용할 수 있도록 설계 및 구현하였습니다.<br/>

### Open API 장애상황 시

장애의 전파를 막기 위해 [Circuit breaker](https://martinfowler.com/bliki/CircuitBreaker.html) 패턴을 적용하였습니다.
구현체로는 [Spring-Retry](https://github.com/spring-projects/spring-retry) 를 사용하였습니다.

## Endpoints

1. <a href="#join">회원가입</a>
2. <a href="#user">회원정보 조회</a>
3. <a href="#login">로그인</a>
4. <a href="#search">키워드 검색</a>
5. <a href="#history">내 검색 히스토리</a>
6. <a href="#favorite">인기 키워드 목록 조회</a>

## Endpoint examples

### <span id="join">회원가입</span>
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

### <span id="user">회원정보 조회</span>
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

### <span id="login">로그인</span>
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

### <span id="search">키워드 검색</span>
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

### <span id="history">내 검색 히스토리</span> 
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

### <span id="favorite">인기 키워드 목록 조회</span>
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

개발환경에서 In-memory 데이터베이스로 사용합니다.

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

JSON Web Token을 사용하여 사용자의 로그인 정보를 생성하고 인증하기 위하여 사용합니다.

#### Gradle
```groovy
implementation 'io.jsonwebtoken:jjwt:0.9.1'
```

### [PBKDF2](https://github.com/m9aertner/PBKDF2)

> A free Java implementation of RFC 2898 / PKCS#5 PBKDF2

회원가입한 사용자의 비밀번호를 PBKDF2 암호화 알고리즘을 통해 암호화하기 위하여 사용합니다.

#### Gradle
```groovy
implementation 'de.rtner:PBKDF2:1.1.4'
```

### [javafaker](https://github.com/DiUS/java-faker)

> This library is a port of Ruby's faker gem (as well as Perl's Data::Faker library) that generates fake data. It's useful when you're developing a new project and need some pretty data for showcase.

테스트 코드에서 임의의 중복되지 않는 데이터를 생성하기 위하여 사용합니다.

#### Gradle
```groovy
testImplementation 'com.github.javafaker:javafaker:1.0.2'
```

### [ehcache](https://www.ehcache.org/)

> JAVA’S MOST WIDELY-USED CACHE

Open API 조회 시, 캐시를 적용하고 관리할 CacheManager 로써 사용합니다.

#### Gradle
```groovy
implementation 'net.sf.ehcache:ehcache:2.10.6'
```
### [Spring-Retry](https://github.com/spring-projects/spring-retry)

> Spring Retry provides an abstraction around retrying failed operations

Open API 조회 시, 장애 상황을 전파하지 않고 극복하기 위해 Circuit breaker 패턴 구현체로 사용하였습니다.

#### Gradle
```groovy
implementation 'org.springframework.retry:spring-retry:1.3.1'
```
