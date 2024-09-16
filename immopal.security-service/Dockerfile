FROM openjdk:17-jdk-alpine
EXPOSE 9000

VOLUME /tmp

COPY src/main/java/ubb/graduation24/immopal/grpc /app/libs

ADD /target/immopal.security-service-0.0.1-SNAPSHOT.jar securityservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/securityservice.jar"]
