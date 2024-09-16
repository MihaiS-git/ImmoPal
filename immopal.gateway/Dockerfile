FROM openjdk:17-jdk-slim
EXPOSE 8080

WORKDIR /gateway
COPY build/libs/immopal.gateway-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT ["java", "-jar", "gateway.jar"]
