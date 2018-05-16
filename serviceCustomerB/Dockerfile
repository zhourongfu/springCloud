FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD serviceCustomer.jar serviceCustomer.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/serviceCustomer.jar"]