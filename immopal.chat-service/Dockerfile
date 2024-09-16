FROM openjdk:17-jdk-alpine
EXPOSE 9005

VOLUME /tmp
ADD build/libs/immopal.chat-service-0.0.1-SNAPSHOT.jar chatservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/chatservice.jar"]
