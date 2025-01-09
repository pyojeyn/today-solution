FROM openjdk:11
ARG JAR_FILE=/build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/app.jar"]