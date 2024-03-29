# Technical Report: React.js and Spring Data REST Application

## Introduction
This technical report documents the analysis, design and implementation of a web application developed using React.js for the frontend and Spring Data REST for the backend. Additionally, this report includes a tutorial with which commands to use in git bash terminal and explaining the following steps:
 - how to copy a project to a new folder;
 - how to add tags to commits and close issues;
 - how to run the Spring-boot application;
 - how to implement new features;
 - how to create and work in a new branch.
   
After this tutorial, an analysis about an alternative to git control version is presented.

## Analysis
The analysis phase involved understanding the requirement to add the new features, which were a jobYears and an email fields to the employee entity and ensuring proper validation of the attributes. Additionally, the requirement included debugging both server and client parts of the application. 

Issues were created for each assignment requirement.


#### Steps to copy the project and run the Spring-boot application

1. Begin by creating a new folder (you can call it "CA1"):
`mkdir CA1`

2. Then, go to the directory where you have the base project and copy it into your new folder:

`cd <projectDirectory>`

`cp -r -exclude=".git" /repository/CA1`

In this second step, we have to exclude **.git** file so we don't create a new repository inside the already existing one.


3. Navigate to the project basic folder directory:

`cd CA1/project/basic/`


4. Run the Spring Boot application:

`./mvn spring-boot:run`


5. Access the application in your browser at http://localhost:8080.



**Note:** At this point, a table with your employee most be displayed. 


## Design
### Backend
Updated the database schema to include the new jobYears (part I) and email (part II) fields in the **Employee** entity, and ensured that the database initialization process remains intact after the addition of the new features.
In order to resolve any potential issues, units tests were implemented.

### Frontend
Modified React components in order to display the new jobYears field, and verified that the frontend correctly interacts with the updated RESTful APIs provided by the backend.
 
## Implementation
### Backend
Updated the Employee entity class to include the jobYears field, by adding the new field to: the class' constructor and validArguments, equals, hashCode and toString methods. Also implemented methods getJobYears and setJobYears so it can be possible to access or update jobYears field.
Note: the validArguments method gives a validation of the constructor arguments. For example, if the user inserts an invalid value, and exception message is thrown.
Verified that the EmployeeRepository interface remains compatible with the updated entity.
Updated the DatabaseLoader class to initialize the database with sample data including the jobYears field. For that, a value needs added to method run (on line that calls "save" method), so the jobYears field as a value assigned when running the application.
Implemented debugging to resolve any issues that appeared during the addition of the new field.
In this stage, when inserting an argument incorrectly, DatabaseLoader class won't be able to be built and an error message should be displayed.

### Frontend Implementation
Modified React components to display the new jobYears field for each employee, on app.js class. Here, JobYears most be added to EmployeeList and Employee classes, which will comunicate with Component function. This constructor will ensure that the employee is properly initialized.

#### Implementation Steps
6. Add a tag to your last commit in order to tag the project's initial version:

`git tag v1.1.0`

`git push --tags`


7. Open your project in IDE (I used intelliJ) and go to package **com.greglturnquist.payroll**. Here you'll find the **Employee** entity and DatabaseLoader. These are the backend classes where you need to add support to the new features.

   
8. Then, go to js package and edit app.js file. This is the fronted class where you add the new features.


9. Finally, develop the necessary unit tests that validate the successful implementation of the new features and debug your application.


10. Add a new employee. Rerun the application and enter the http://localhost:8080 url in your browser. Enter in the terminal the following command:

`curl -X POST localhost:8080/api/employees -d "{\"firstName\": \"Peregrin\", \"lastName\": \"Took\", \"description\": \"Pipe Smoker\", \"jobYears\": \"3\"}" -H 

"Content-Type:application/json`


**Note:** After entering this command, refresh your localhost so the new employee is displayed. *(This addition isn't permanent since we're not adding it to our code)*

## Commit your changes
In this assignment, there were two ways to work in our project: without branchs, and with branchs. 

### Without branches
After debbuging your application and ensuring that the implementation was successful, commit your changes into the remote repository.

1. Add your changes to the staging area:

`git add .`



2. Commit your changes. Here you most add a message to your commit that describes briefly the changes:

`git commit -m "commit message"`



2.1 Close an issue when you commit your changes:

`git commit -m "Close #issueNumber. Commit message"`



3. Finally, add your changes to the remote repository:

`git push`


You can always use the command `git status` to track your working copy state: if you have untracked files, uncommited changes or info to be added to remote repository.

### With branches
When working in a team, it is usual to work in branches. This helps you to work on your tasks without conflicting with other team elements work. 

1. Before adding any change to the project, create a new branch that describes what you will be working on (for example, implementing a new email-field):

`git branch email-field`

`git checkout email-field`



2. Now, that you are working in the new branch, you can implement the new feature email-field to **Employee** entity (just as described in sub-header **Implementation Steps**).



3. After implementing the new feature, testing and debbuging the application, it is time to add your work to the master branch.

`git add .`

`git commit -m "Closed #issueNumber. New feature email-field implemented and tested."`

`git push origin email-field`



4. Checkout to master and merge the two branches (email-field with master):

`git checkout master`

`git merge --no-ff email-field`

**Note:** Command `git merge --no-ff email-field` is used when there is not commits added to master branch and you want your new branch to save is data history.


After merging your branch, you can create a new tag that checks a new version of your project. (`git tag versionX` + `git push --tags`)
To finish the second part of the assignment it was requested to add a verification to the email field, so it only acceptes email that have a "@" signal. For this part, a new branch was created as well - fix-invalid-email - and the steps were repeated.


## Alternative to Git version control
To conclude this class assignment, it was requested to present an alternative solution for version control that is not based on Git. 

### Mercurial Analysis
Mercurial (hg) is one of many alternatives to Git. After some search it was clear that there are many similarities between Git and Mercurial, but also some differences in its implementation and workflow.


Mercurial is often considered more user-friendly and simpler to use compared to Git. Its command set is generally more straightforward, making it easier for beginners to learn and navigate. Mercurial follows a consistent behavior across different commands, which can make it easier to predict the outcome of operations. This means that when you execute a command in Mercurial, you can expect it to behave in a certain way regardless of the specific context or situation.

Mercurial provides a feature called "bookmarks" that serves as a lightweight alternative to traditional named branches. This bookmarks are pointers to specific commits - similar to Git's tags. This technology also as the capability to be extended through the use of plugins or extensions that enables users to extend its functionality according to their specific requirements. This extensions can provide flexibility and adaptability to users workflow.


Nevertheless, Mercurial may lag behind Git in terms of widespread adoption and community size. Consequently, the ecosystem around Mercurial may be less extensive, with fewer tools, plugins, and community support available. Mercurial's branching model, although functional, might not offer the same level of flexibility as Git, particularly for complex workflows like feature branching and release management. Moreover, Mercurial lacks a staging area like Git's, which could limit users' ability to review and selectively stage changes before committing them. Lastly, while Mercurial is efficient for many tasks, Git is often praised for its speed and performance, especially with large repositories and complex operations like branching and merging.


In summary, while Mercurial excels in simplicity, consistency, and ease of use, making it a good choice for beginners and projects with straightforward version control needs, Git offers more flexibility, power, and ecosystem support, making it preferred for larger projects, open-source collaboration, and more complex workflows.


### Mercurial Implementation

Setting up and implementing Mercurial involves the following steps:


1. Initialize a new Mercurial repository in the desired directory. This creates a new repository where project files and version history will be stored.

`hg init`


2. Some basic commands for managing changes, committing them to the repository, and synchronizing with remote repositories are:

`hg add`

`hg commit`

`hg pull`

`hg push`


3. When using bookmarks it becomes possible to isolate changes and experiment with different features or fixes. The command to create a bookmark is:

`hg bookmark`


4. If you want to work on a specific named branch, you can update your working directory to the latest change-set on that branch using the following command:

`hg update <branch_name>`


5. You can install and manage Mercurial extensions to extend its functionality. Extensions can be installed from the Mercurial Package Index (PyPI) or by downloading them manually and enabling them in the configuration file.


6.  When sharing repositories and synchronizing changes you can use the commands `hg push` and `hg pull`. But, to resolve conflicts and merge changes you may want to use the following command:

`hg merge`


In summary, while Git and Mercurial share many similarities, their differences in ecosystem size, branching models, and target user base influence their suitability for different contexts. Mercurial excels as a user-friendly option for beginners and smaller projects, offering simplicity and flexibility. Meanwhile, Git's widespread adoption and robust ecosystem make it a preferred choice for larger teams and complex projects, providing advanced features and seamless integration with popular development platforms. Ultimately, the choice between Git and Mercurial depends on factors such as project size, team preferences, and specific workflow requirements.
