# I have not tested this yet
# And where do we spin up our PG container ??
FROM openjdk:22-jdk-slim
MAINTAINER daynum
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]