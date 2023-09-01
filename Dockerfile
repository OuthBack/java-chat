FROM openjdk:17-alpine
MAINTAINER github/outhback
COPY ./target/app-0.0.1-SNAPSHOT.jar /app/app-0.0.1-SNAPSHOT.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]
