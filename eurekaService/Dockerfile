FROM openjdk:8-jdk-alpine
VOLUME /tmp
WORKDIR /ROOT
EXPOSE 8761
CMD ["java", "-version"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","${project.build.finalName}.jar"]