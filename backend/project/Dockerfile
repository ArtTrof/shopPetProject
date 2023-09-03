FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /project
COPY pom.xml .
RUN #mvn dependency:go-offline
COPY . .
RUN mvn install
FROM openjdk:17-alpine
WORKDIR /project
COPY --from=builder /project/target/project-0.0.1-SNAPSHOT.jar project.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "project.jar"]