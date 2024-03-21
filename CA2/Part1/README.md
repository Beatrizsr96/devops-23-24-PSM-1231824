# Technical Report for CA2 Part 1: Build Tools with Gradle

## Introduction

This technical report documents the implementation of Part 1 of Class Assignment 2 (CA2), focusing on mastering build tools with Gradle. The assignment tasks encompass setting up an example application and applying Gradle for task automation, including server execution, unit testing, and file management.

## Setup and Initial Configuration
### Cloning and Setting Up the Example Application
Clone the Example Application:
Begin by cloning the example application repository provided in the assignment description:

Copy code:
``` bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git CA2/Part1
cd CA2/Part1
```

### Initial Commit:
Add the cloned project to your repository and commit the changes:

Copy code:
``` bash
git add .
git commit -m "Cloned the example application for CA2 Part 1"
```

## Implementing Gradle Tasks
### Task 1: Execute the Server

#### Issue Creation:
Create an issue to track the implementation of the server execution task:

Issue Title: Implementing a Gradle Task to Execute the Server
Description: Track the task of implementing a Gradle task to execute the server.
Assignee: Your Name

#### Implementation:
Modify the build.gradle file to include a custom task for executing the server:

Copy code:
```groovy
// Define task to execute the server
task runServer(type: JavaExec, dependsOn: 'classes') {
    group = 'DevOps'
    description = 'Launches a chat server that listens on port 59001'

    classpath = sourceSets.main.runtimeClasspath
    main = 'basic_demo.ChatServerApp'
    args '59001'
}
```

#### Running the Task:
Execute the server task using:

Copy code:
```
bash
./gradlew runServer
```

#### Committing the Changes:
Commit the changes to your repository:

Copy code:
```
bash
git add .
git commit -m "Implemented a Gradle task to execute the server"
```

### Task 2: Simple Unit Test
#### Issue Creation:
Create an issue to track the implementation of a simple unit test:

Issue Title: Implementing a Simple Unit Test
Description: Track the task of implementing a simple unit test for the application.
Assignee: Your Name
#### Implementation:
Write a simple unit test in src/test/java/basic_demo/AppTest.java.

#### Adding JUnit Dependency:
Ensure JUnit 4.12 dependency is added to build.gradle.

#### Running the Test:
Execute the test using:

Copy code:
```bash
./gradlew test
```

#### Committing the Changes:
Commit the changes to your repository.

### Task 3: Backup of Sources
#### Issue Creation:
Create an issue to track the implementation of the source files backup task:

Issue Title: Implementing a Gradle Task to Backup Source Files
Description: Track the task of implementing a Gradle task to backup source files.
Assignee: Your Name

#### Implementation:
Add a task in build.gradle to backup source files to a backup directory.

#### Running the Task:
Execute the backup task.

#### Committing the Changes:
Commit the changes to your repository.

### Task 4: Archive of Sources
#### Issue Creation:
Create an issue to track the implementation of the source files archive task:

Issue Title: Implementing a Gradle Task to Create a Zip Archive of Source Files
Description: Track the task of implementing a Gradle task to create a zip archive of source files.
Assignee: Your Name

#### Implementation:
Add a task in build.gradle to create a zip archive of the source files.

#### Running the Task:
Execute the archive task.

#### Committing the Changes:
Commit the changes to your repository.

## Finalizing the Assignment
Tagging the Repository:
Tag your repository to signify the completion of CA2 Part 1.


## Conclusion
Part 1 of CA2 provided valuable experience in utilizing Gradle for build automation tasks. Through implementing server execution, unit testing, and file management tasks, a solid understanding of Gradle's capabilities and practical applications was gained. This lays a strong foundation for tackling more complex build scenarios in the future.
