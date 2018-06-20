FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/configService.jar configService.jar
EXPOSE 7000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/configService.jar"]