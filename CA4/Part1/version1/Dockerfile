FROM gradle:jdk17 AS builder

WORKDIR /CA4/Part1

RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

# Change the working directory to the root directory of the cloned repository
WORKDIR /CA4/Part1/gradle_basic_demo
RUN chmod +x gradlew

RUN ./gradlew build


FROM openjdk:17-jdk-slim

WORKDIR /CA4/Part1

# Copy the built JAR file from the first stage
COPY --from=builder /CA4/Part1/gradle_basic_demo/build/libs/*.jar CA4/Part1.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-cp", "CA4/Part1.jar", "basic_demo.ChatServerApp", "59001"]