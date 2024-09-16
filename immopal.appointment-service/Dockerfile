FROM openjdk:17-jdk-alpine
EXPOSE 9004
EXPOSE 9054

VOLUME /tmp
ADD build/libs/immopal.appointment-service-0.0.1-SNAPSHOT.jar appointmentservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/appointmentservice.jar"]
