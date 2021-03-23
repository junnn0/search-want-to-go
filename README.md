# Search-where-to-go

Kakao, Naver의 장소 Open API를 사용하여 검색 기능을 제공하는 프로젝트입니다. 

## 구성

### search-where-to-go-web
* [Vuejs](https://github.com/vuejs/vue) Framework 로 개발된 프로젝트로 WEB의 역할을 합니다.
* 8081 포트로 동작합니다.
  
### search-where-to-go-api
* [Spring-Boot](https://spring.io/projects/spring-boot) framework로 개발된 프로젝트로 API의 역할을 합니다.
* 8080 포트로 동작합니다.

## 실행

```shell
docker-compose -f docker-compose.yml up
```

동일한 환경에서의 실행을 위해 Containerized 된 실행방법을 제공합니다.<br/>
WEB과 API의 각 디렉터리에 위치한 Dockerfile로 이미지를 빌드하고, 컨테이너 환경에서 동작할 수 있도록 합니다.

## 접근

```
http://localhost:8081/
```
