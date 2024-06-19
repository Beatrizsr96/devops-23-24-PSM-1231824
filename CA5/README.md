# Technical Report for Class Assigment 5

This technical report describes the steps taken to successfully complete Class Assignment 5. The objective was to integrate Jenkins with Docker, enabling automated build, test, and deployment processes. The report includes the commands used, the issues encountered, and the solutions implemented.


## Install Jenkins (using Docker container)

1. Open docker on your desktop. Go to your terminal and run this command: `docker run -p 8080:8080 jenkinsci/blueocean`.
   This command will run an image that will set up your container to run jenkins.

**Note:** When I ran the image provided, some plugins weren't installed due to jenkins version, provided in that image, being outdated. Instead I used jenkins/jenkins:lts (image with jenkins latest version).

2. Start your container. Run the command `docker run -p 8080:8080 jenkins/jenkins:lts` to have access to your initial password. You'll need it to unlock jenkins.
   Or, you can access in docker desktop your container's files, and look at `var/jenkins_home/secrets/initialAdminPassword` directory. Save the file and copy the code inside that file.

3. Go to `http://localhost:8080`, insert your **initialAdminPassword**, install the plugins you want to add to Jenkins, log in as user (instead of admin) and you're good to go and create your first pipeline.



## Create Jenkinsfile
### for gradle-basic-demo project in CA2/Part1

**Note:** In order to pratice and construct my Jenkinsfile I creted a new pipeline where I wrote the Pipeline script with the asked requirements for the Jenkinsfile.

After having your script ready, go to your repository's, create 'CA5/Part1' directory and create a **Jenkinsfile** with the following content:

```Jenkinsfile
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out...'
                git 'https://github.com/Beatrizsr96/devops-23-24-PSM-1231824'
            }
        }

        stage('Assemble') {
            steps {
                echo 'Assembling...'
                sh '''
                    cd ./CA2/Part1/gradle_basic_demo
                    ls -la
                    chmod +x ./gradlew
                    ./gradlew assemble
                '''
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
                sh '''
                    cd ./CA2/Part1/gradle_basic_demo
                    ls -la
                    ./gradlew test
                '''
            }
            post {
                always {
                    junit '**/build/test-results/**/*.xml'
                }
            }
        }

        stage('Archiving') {
            steps {
                echo 'Archiving...'
                archiveArtifacts 'CA2/Part1/gradle_basic_demo/build/distributions/*'
            }
        }
    }
}
```
This Jenkinsfile starts by checking out the code and building it. Then it runs tests, generates documentation and archives the resulting artifact.

**Note:** we don't use the `build` task because, in gradle, it also executes the tests.

**Don't forget to commit and push your changes into your repository.**


## Create Jenkins pipeline (Part1)

1. Go to Jenkins **main page** and click on **New Item**. Give a name to your new item (e.g., CA5_Part1_pipeline) and select **Pipeline**.

2. Scroll to **Pipeline** section and select **Pipeline script from SCM**.
   Where says **Script Path** insert your Jenkinsfile relative path (e.g. `CA5/Part1/gradle-basic-demo/Jenkinsfile`)

3. Next, in **Repository URL** insert your repository URL.

Click the **Save** button. Now, use **Build Now** to execute the Job.

Here, You can select the **build number** of the Job to get details on the execution. If you go to **Console Output**, you can see what is happening during the build process of your pipeline.


**Note:** Due to some problems with gradle build, I noticed that my Jenkins was using a different version of gradle, so I had to go to **Manage Jenkins/Tools** and add a Gradle version compatible with my project (gradle 8.6).

I also had to install "Checks API" plugin to publish project info in Jenkins. This plugin provides an API for reporting and consuming feedback on code quality checks and test results.


## Create Jenkinsfile
### for react-and-spring-data-rest-basic project in CA2/Part2

**Note:** In order to pratice and construct my Jenkinsfile I creted a new pipeline where I wrote the Pipeline script with the asked requirements for the Jenkinsfile.

I had to install the following plugins:
- **HTML publisher plugin** to be able to run the `publishHTML`section in stage `Generate Javadoc`
- **Docker pipeline**, **Docker API Plugin**, **Docker Commons Plugin** and **Docker plugin** to build and publish my docker image in **DockerHub**.


After having your script ready, go to your repository's CA5 directory, create 'Part2' directory and create a new **Jenkinsfile** with the following content:

```Jenkinsfile
pipeline {
    agent any
    
    environment {
        DOCKER_CREDENTIALS_ID = "docker-hub-credentials"
        DOCKER_IMAGE = "1231824-ca5-part2"
        DOCKER_TAG = "${env.BUILD_ID}"
    }


    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out...'
                git 'https://github.com/Beatrizsr96/devops-23-24-PSM-1231824.git'
            }
        }

        stage('Assemble') {
            steps {
                echo 'Assembling...'
                sh '''
                    cd ./CA2/Part2/react-and-spring-data-rest-basic
                    chmod +x ./gradlew
                    ./gradlew assemble
                '''
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
                sh '''
                    cd ./CA2/Part2/react-and-spring-data-rest-basic
                    ./gradlew test
                '''
            }
            post {
                always {
                    junit '**/build/test-results/**/*.xml'
                }
            }
        }

        stage('Generate Javadoc') {
            steps {
                echo 'Generating Javadoc...'
                sh '''
                    cd ./CA2/Part2/react-and-spring-data-rest-basic
                    ./gradlew javadoc
                '''
            }
            post {
                success {
                    publishHTML(target: [
                        allowMissing         : false,
                        alwaysLinkToLastBuild: false,
                        keepAll              : true,
                        reportDir            : 'CA2/Part2/react-and-spring-data-rest-basic/build/docs/javadoc',
                        reportFiles          : 'index.html',
                        reportName           : 'Javadoc'
                    ])
                }
            }
        }

        stage('Archiving') {
            steps {
                echo 'Archiving...'
                archiveArtifacts artifacts: 'CA2/Part2/react-and-spring-data-rest-basic/build/libs/*.war', allowEmptyArchive: true
            }
        }


        stage('Publish Image') {
            steps {
                script {
                    dir('CA5/Part2') {
                        echo 'Building and publishing Docker image...'
                        docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                            dir('CA2/Part2/react-and-spring-data-rest-basic') {
                                def customImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                                customImage.push()
                                customImage.push('latest')
                            }
                        }
                    }
                }
            }
        }
    }
}
```

Having the first Jenkinsfile as a base to build this second Jenkinsfile, this one builds a Docker image from a specific directory (CA2/Part2/react-and-spring-data-rest-basic), tags it with the Jenkins build ID, and pushes it to Docker Hub using provided Docker credentials.

To be able to push your image, you'll have to create `DOCKER_HUB_CREDENTIALS` in Jenkins: Go to **Manage Jenkins > Credentials**, click on "global" and **Add credential**. (Here you'll need to know your Docker credentials (username and password)).

*Note:* Since the Jenkinsfile is located in /CA5/Part2 and the Gradle project is located in /CA2/Part2/react-and-spring-data-rest-basic, ensure to specify the Script Path in the Jenkins job configuration as CA5/Part2/Jenkinsfile.

**Don't forget to commit and push your changes into your repository.**


## Create dockerfile to run your image

Next step will be create a dockerfile that generates an image with Tomcat

```Dockerfile
FROM tomcat:9-jdk17-openjdk

COPY ./build/libs/*.war /usr/local/tomcat/webapps/

EXPOSE 8080
```

This Dockerfile sets up a Docker image that uses Tomcat 9 with OpenJDK 17 as the base image.
Then, it copies any .war files from the local ./build/libs/ directory into Tomcat's webapps directory within the container.
Exposes port 8080, allowing external access to web applications deployed within the container.

**Don't forget to commit and push your changes into your repository.**


## Create the Jenkins pipeline (Part2)


1. Go to Jenkins **main page** and click on **New Item**. Give a name to your new item (e.g., CA5_Part2_pipeline) and select **Pipeline**.

2. Scroll to **Pipeline** section and select **Pipeline script from SCM**.
   Where says **Script Path** insert your Jenkinsfile relative path (e.g. `CA5/Part2/gradle-basic-demo/Jenkinsfile`)

3. Next, in **Repository URL** insert your repository URL.

Click the **Save** button. Now, use **Build Now** to execute the Job.

Here, You can select the **build number** of the Job to get details on the execution. If you go to **Console Output**, you can see what is happening during the build process of your pipeline.

After this, you can access the application at http://localhost:8080/basic-0.0.1-SNAPSHOT

## Conclusions

The completion of Class Assignment 5 involved integrating Jenkins with Docker to automate build, test, and deployment processes. To conclude the assignment successfully a Dockerfile was created to package the project's artifacts (*.war files) into a Tomcat-based Docker image, ensuring consistency between development and production environments. And two Jenkins pipelines were configured (Part1 and Part2), demonstrating the automation of build, test, documentation, artifact archiving and Docker image deployment.

In conclusion, integrating Jenkins with Docker proved to be a streamline for development workflows, improving efficiency and adhering to best practices in continuous integration and deployment. 