FROM gradle:jdk17 AS builder

WORKDIR /CA4/Part1

COPY ./gradle_basic_demo .

FROM openjdk:17-jdk-slim

WORKDIR /CA4/Part1

# Copy the built JAR file from the first stage
COPY --from=builder /CA4/Part1/build/libs/*.jar ca4-part1.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-cp", "ca4-part1.jar", "basic_demo.ChatServerApp", "59001"]