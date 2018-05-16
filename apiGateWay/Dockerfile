FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD apiGateWay.jar apiGateWay.jar
EXPOSE 8088
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/apiGateWay.jar"]