#Technical Report: React.js and Spring Data REST Application

##Introduction
This technical report documents the analysis, design and implementation of a web application developed using React.js for the frontend and Spring Data REST for the backend. Additionally, a tutorial of how to run the application is provided. 

##Analysis
The analysis phase involved understanding the requirement to add a new field jobYears to the employee entity and ensuring proper validation of attributes. Additionally, the requirement includes debugging both server and client parts of the application.

##Design
###Backend
Updated the database schema to include the new jobYears field in the Employee entity, and ensured that the database initialization process remains intact after the addition of the new filed.
Then, added validation to ensure that the jobYears field only accepts integer values. In order to resolve any potential issues, units tests were implemented.

###Frontend
Modified React components in order to display the new jobYears field, and verified that the frontend correctly interacts with the updated RESTful APIs provided by the backend.

##Implementation
###Backend
Updated the Employee entity class to include the jobYears field, by adding the new field to: the class' constructor and validArguments, equals, hashCode and toString methods. Also implemented methods getJobYears and setJobYears so it can be possible to access or update jobYears field.
Note: the validArguments method gives a validation of the constructor arguments. For example, if the user inserts an invalid value, and exception message is thrown.
Verified that the EmployeeRepository interface remains compatible with the updated entity.
Updated the DatabaseLoader class to initialize the database with sample data including the jobYears field. For that, a value needs added to method run (on line that calls "save" method), so the jobYears field as a value assigned when running the application.
Implemented debugging to resolve any issues that appeared during the addition of the new field.
In this stage, when inserting an argument incorrectly, DatabaseLoader class won't be able to be built and an error message should be displayed.

###Frontend Implementation
Modified React components to display the new jobYears field for each employee, on app.js class. Here, JobYears most be added to EmployeeList and Employee classes, which will comunicate with Component function. This constructor will ensure that the employee is properly initialized.


##Tutorial on How to run the application

###Clone the repository:
git clone <repository-url>

###Navigate to the project basic folder directory:
cd CA1/project/basic/

###Run the Spring Boot application:
./mvn spring-boot:run

Access the application in your browser at http://localhost:8080.

Note: At this point, a table with your employee most be displayed. 

###Add a new employee:
curl -X POST localhost:8080/api/employees -d "{\"firstName\": \"Peregrin\", \"lastName\": \"Took\", \"description\": \"Pipe Smoker\", \"jobYears\": \"3\"}" -H "Content-Type:application/json"

Note: After entering this command, refresh your localhost so the new employee is displayed.
