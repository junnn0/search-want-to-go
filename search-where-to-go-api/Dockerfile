FROM gradle:jdk-alpine
MAINTAINER plus4070@gmail.com
WORKDIR /home/gradle/search-where-to-go-api
EXPOSE 8080
USER root
RUN apk update
ENV GRADLE_USER_HOME /home/gradle/search-where-to-go-api
COPY . /home/gradle/search-where-to-go-api
RUN ./gradlew build -x test -x spotlessJava -x spotlessJavaCheck


FROM java:jre-alpine
WORKDIR /home/gradle/search-where-to-go-api
COPY --from=0 /home/gradle/search-where-to-go-api/build/libs/search-where-to-go-api.jar .
ENTRYPOINT java -jar search-where-to-go-api.jar
