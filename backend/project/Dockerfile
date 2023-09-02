FROM openjdk:17-alpine
ADD target/project-0.0.1-SNAPSHOT.jar project.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "project.jar"]