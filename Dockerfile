# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
MAINTAINER ........

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=web-app/target/web-app-DEV-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} solace-jms.jar

# Run the jar file
ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}","-jar","/solace-jms.jar"]

