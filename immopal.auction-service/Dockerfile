FROM openjdk:17-jdk-alpine
EXPOSE 9006

VOLUME /tmp
ADD build/libs/immopal.auction-service-0.0.1-SNAPSHOT.jar auctionservice.jar
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/auctionservice.jar"]
