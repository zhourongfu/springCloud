FROM openjdk:8-jdk-alpine
MAINTAINER 646154945@qq.com
ADD target/eureka.jar /app.jar
ENV EUREKA_IP localhost
VOLUME ["/data/logs"]
EXPOSE 8761
ENTRYPOINT eureka-k8s.sh && java -Djava.security.egd=file:/dev/./urandom -jar /app.jar