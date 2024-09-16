FROM openjdk:17-jdk-alpine
EXPOSE 9003
EXPOSE 9053

VOLUME /tmp
ADD build/libs/immopal.agency-service-0.0.1-SNAPSHOT.jar agencyservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/agencyservice.jar"]
