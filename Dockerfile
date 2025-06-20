FROM openjdk:17-jdk-slim
LABEL authors="shaur"

ARG JAR_FILE=target/Whistleblower-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
