FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD serviceProducer.jar serviceProducer.jar
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/serviceProducer.jar"]