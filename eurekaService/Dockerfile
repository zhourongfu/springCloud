FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/eurekaService.jar eurekaService.jar
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eurekaService.jar"]