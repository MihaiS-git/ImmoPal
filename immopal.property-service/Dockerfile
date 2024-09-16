FROM openjdk:17-jdk-alpine
EXPOSE 9002
EXPOSE 9052

VOLUME /tmp
ADD build/libs/immopal.property-service-0.0.1-SNAPSHOT.jar propertyservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/propertyservice.jar"]
