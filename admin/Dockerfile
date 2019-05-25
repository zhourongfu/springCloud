FROM openjdk:8-jdk-alpine
MAINTAINER 646154945@qq.com
VOLUME ["/data/logs"]
EXPOSE [8888]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/${project.build.finalName}.jar"]