# Technical Report CA4 Part2

In this second part of the forth Class Assigment, a `docker-compose.yml` file was created. This file defines a multi-container Docker application with two services: `db` and `web`. Each service is built from a specific **Dockerfile** and has its own configuration.

To achieve this, the following steps were taken:

### 1.Dockerfile-compose.yml Basic Version


```yml

services:
  db:
    build:

      # The build context is set to the current directory, meaning Docker will look in the current directory for the resources needed to build the image.
      context: .

      # dockerfile to be used to build the image for db service
      dockerfile: Dockerfile-db
    ports:
      - "8082:8082"
      - "9092:9092"
   
  web:
    build:
      context: .

      # dockerfile to be used to build the image for web service
      dockerfile: Dockerfile-web
    ports:
      - "8080:8080"

       # db service must be started before the web service.
       depends_on:
      - "db"

```


### 2.Web Dockerfile

For web service, we'll need a dockerfile similar to this:

```dockerfile
FROM openjdk:17-jdk-slim

# Install additional utilities
RUN apt-get update -y && apt-get install -y git unzip

RUN mkdir /app

WORKDIR /app/


# Clone your Spring Boot application repository
RUN git clone https://github.com:Beatrizsr96/devOps-23-24-JPE-1231824.git

# Set the working directory
WORKDIR /app/devOps-23-24-JPE-1231824/CA2/part2/part2_gradle


# Ensure the gradlew script is executable and build the application
 RUN chmod +x ./gradlew && ./gradlew clean build

# Configure the container to run as an executable
 ENTRYPOINT ["./gradlew"]
 CMD ["bootRun"]
```

In the `git clone` and ´WORKDIR` commands you may change to your own repository (or another that you would like to use).

**Note:** Because of version incompability of CRLF from Windows to Inux, I copied manually the project to CA4/Part2 and changed CRLF to LF. Because of this change, dockerfile-web had to be updated. This was the updated version:


```Dockerfile
FROM gradle:jdk21
RUN apt-get update && apt-get install -y dos2unix

WORKDIR /app

COPY . /app

# Configure the container to run as an executable
RUN chmod +x ./gradlew && dos2unix ./gradlew

CMD ["./gradlew", "bootRun"]
``` 

### 3.Database Dockerfile

```Dockerfile
FROM ubuntu

# Installing dependencies
RUN apt-get update && \
  apt-get install -y openjdk-17-jdk-headless && \
  apt-get install unzip -y && \
  apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

# Download H2 Database and run it
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O /opt/h2.jar

EXPOSE 8082
EXPOSE 9092

# Start H2 Server
CMD ["java", "-cp", "/opt/h2.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]
```

### 4.docker-compose.yml Update

After this, I changed my docker-compose.yml file, and added volumes and networks information for connection:

```yml
services:
  db:
    build:
      context: .
      dockerfile: Dockerfile-db
    ports:
      - "8082:8082"
      - "9092:9092"

    # Mounts the data directory on the host machine /usr/src/data-backup
    volumes:
      - ./data:/usr/src/data-backup
    networks:
      
      # Assigning a static IP address to the default network
      default:
        ipv4_address: 192.168.33.11

  web:
    build:
      context: .
      dockerfile: Dockerfile-web
    ports:
      - "8080:8080"
    networks:
      default:
        ipv4_address: 192.168.33.10
    depends_on:
      - "db"

networks:
  default:
    ipam:
      driver: default
      config:
        - subnet: 192.168.33.0/24
```

### Docker-compose.yml Summary
 

- db Service: A database service with ports 8082 and 9092 exposed, data volume mounted, and a static IP address `192.168.33.11`.

- web Service: A web service with port 8080 exposed, dependent on the db service, and a static IP address `192.168.33.10`.

- Network: Both services are connected to the same custom network with the subnet `192.168.33.0/24`.


This configuration ensures that the web service can reliably communicate with the db service using its static IP address and that both services have access to the necessary network resources and ports.

### Run docker-compose.yml File

After creating your Dockerfiles and docker-compose.yml, you can run the following command to start the services web and database:

```bash
docker-compose up --build
```

To access the services running you may use:

- Spring Boot Application: http://localhost:8080

- H2 Database Console: http://localhost:8082


You may also run the following command to enter the h2 shell:

```bash
docker exec -it h2-db /bin/bash
```

Finally, to stop the services, we run the following command:

```bash
docker-compose down
```

**Note:** Unlike docker stop, docker-compose down stops and removes all the containers, and networks created by docker-compose up.

## Conclusions
This assignment provided an introduction to Docker and Docker Compose, essential tools for software deployment. By completing the steps outlined, several key concepts, practices in containerization and multi-container application management were demonstrated.
Overall, this assignment encapsulates many of the core principles and practical skills needed for modern software development and DevOps practices. Mastery of these concepts and tools not only improves development efficiency but also prepares for more advanced topics in microservices, cloud computing, and continuous integration/continuous deployment workflows.

## Alternative to Docker (Kubernets)

Kubernets manages and organizes multiple containers across many computers (cluster). It has as main control point an API Server, decides which computer runs what, handles various controllers. It uses **Kubelet** to manaige containers on each computer.
It's reat for running large, complex applications with many parts and helps with scaling (adding more resources as needed), balancing loads, and recovering from failures automatically.

### Main differences between Docker and Kubernets

1.Scope:

- Docker is about creating and running containers.


- Kubernetes is about managing many containers across multiple machines.


2.Complexity:

- Docker is simpler and easier to start with.


- Kubernetes is more complex but powerful for managing lots of containers.


3.Integration:

- Docker can be used alone or as part of Kubernetes for container orchestration.


- Kubernetes can use Docker to run containers but also works with other tools.


## Conclusions about this alternative

Both are essential tools in modern application development and deployment, often used together. Docker simplifies container creation, while Kubernetes handles large-scale container management.
