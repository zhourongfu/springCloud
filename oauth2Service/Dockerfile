FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/oauth2Service.jar oauth2Service.jar
EXPOSE 8881
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/oauth2Service.jar"]