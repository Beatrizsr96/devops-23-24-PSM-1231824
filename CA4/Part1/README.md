# Technical Report CA4 Part1

The first part of this Class Assignment was divided in **two versions**: 
- **version 1** runs the application's server and client in Dockerfile; 
- **version 2** runs the server in host computer and the client in docker.

## Version 1 Dockerfile (break through)

1. First step when creating a Dockerfile is to add an image to build. 

Once we have a gradle wrapper, we need to add the instruction `AS builder` to the `FROM` directive to specify that this image will be used as the **builder stage**.
```Dockerfile
FROM gradle:jdk17 AS builder
```

2. Next the working directory is setted.

   The `WORKDIR` instruction sets the working directory for any subsequent
```Dockerfile
WORKDIR /CA4/Part1
```

3. Then, we `clone` the repository with the chat-client-server app.
```Dockerfile
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git
```

4. After cloning the repository, go to `gradle_basic_demo` directory and set your permissions to enable execution.
```Dockerfile
WORKDIR /CA4/Part1/gradle_basic_demo
RUN chmod +x gradlew
```

5. Run the gradle's wrapper build command
```Dockerfile
RUN ./gradlew build
```

6. After running gradle wrapper, the final image needs to be specified (in this case, the image contains a slim version of the jdk-17 runtime environment).
  
The directory inside the container also needs to be specified for the final stage.
```Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /CA4/Part1
```

7. The following command copies the `.jar` file built in the first stage to the working directory of the final image. 

The path `/ca4/Part1/gradle_basic_demo/build/libs/*.jar` is the location of the built `.jar` file in the builder stage, and it is copied to `CA4/Part1.jar` in the final image.
```Dockerfile
COPY --from=builder /ca4/Part1/gradle_basic_demo/build/libs/*.jar CA4/Part1.jar
```

8. `EXPOSE` is used to specify the port the container listens on at runtime.

   The image works fine even without this instruction given further commands, but it's important to document the ports the container listens on.
```Dockerfile
# Expose the port the chat server will run on
EXPOSE 59001
```

9. Finally, We define the command that will be executed when the container starts.
  
This command runs the Java application from the specified `.jar` file (`ca4-part1.jar`) and sets the main class to `basic_demo.ChatServerApp`, with port number **59001** as an argument (where server will be listening).
```Dockerfile
ENTRYPOINT ["java", "-cp", "ca4-part1.jar", "basic_demo.ChatServerApp", "59001"]
```

10. To execute the chat server we need to run the gradle task that starts the server.

In the end, the **Dockerfile** looks like this:
```Dockerfile:
#Create image
FROM gradle:jdk17 AS builder

WORKDIR /CA4/Part1

RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

# Change the working directory to the root directory of the cloned repository
WORKDIR /CA4/Part1/gradle_basic_demo
RUN chmod +x gradlew

RUN ./gradlew build

FROM openjdk:17-jdk-slim

WORKDIR /CA4/Part1

# Bundle app source
COPY --from=builder /CA4/Part1/gradle_basic_demo/build/libs/*.jar CA4/Part1.jar

ENTRYPOINT ["java", "-cp", "CA4/Part1.jar", "basic_demo.ChatServerApp", "59001"]
```

Then, went to repository root and used the following command on the bash terminal: `docker build -t ca4-part1 . `

(-t tags the image with a name (ca4-part1 in this case).

Go to your docker desktop app, and confirm that your image is already available.

## Run image and server
Run command `docker run -p 59001:59001 ca4-part1`. This command enables the image to be ran and leads to an automatic creation of a container, which can be seen  in the section "containers" of the docker desktop app.

After running the previous command, the message *"The chat server is running..."* is displayed, indicating that the server is running and waiting for a request from the client.

## Run client
If I open another git bash terminal and go to the basic-gradle-basic directory (in my case: ~/CA2/Part1/gradle_basic_demo) and run the command `./gradlew runClient`, a java window opens, indicationg that the client is operating properly.

To confirm that my image was added to docker I ran the command `docker images` and the output was:

```bash
REPOSITORY               TAG       IMAGE ID       CREATED         SIZE
ca4-part1                latest    cfb2fef5332d   3 hours ago     410MB
jamj2000/oracle-xe-21c   latest    3697feff54f9   22 months ago   6.53GB
```


## Version 2 Dockerfile

```Dockerfile
# First stage: Build the application
FROM gradle:jdk17 AS builder

WORKDIR /CA4/Part1

COPY ./gradle_basic_demo .

# Second stage: Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /CA4/Part1

# Copy the built JAR file from the first stage
COPY --from=builder /CA4/Part1/build/libs/*.jar ca4-part1.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-cp", "ca4-part1.jar", "basic_demo.ChatServerApp", "59001"]
```

## Conclusions
Comparing both Dockerfile versions, we can conclude that they achieve the same goal of building a Docker image for a Gradle-based Java application.
The main difference lies in how they handle the source code: Dockerfile **version 1** clones the repository within the Dockerfile itself, while Dockerfile **version 2** copies the project source from the host machine. The choice between them depends on factors such as whether you want to manage source code externally or prefer a more contained approach within the Dockerfile.

