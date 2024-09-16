FROM openjdk:17-jdk-alpine
EXPOSE 9007

VOLUME /tmp

ADD target/immopal.email-0.0.1-SNAPSHOT.jar emailservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/emailservice.jar"]
