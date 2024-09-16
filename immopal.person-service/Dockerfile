FROM openjdk:17-jdk-alpine

EXPOSE 9001
EXPOSE 9051

VOLUME /tmp
ADD build/libs/immopal.person-service-0.0.1-SNAPSHOT.jar personservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/personservice.jar"]
