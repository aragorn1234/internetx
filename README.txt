Steps to start the application.

	1. Import internetx folder in your favorite IDE. 
	2. Adapt application.properties. change spring.datasource.url, spring.datasource.username and spring.datasource.password
	3. Create database schema. Pay attention, that column password in table user has to have length of 60 characters not 50!!!!!!! If this is not changed, the
	   whole application does not work!!!!!!!
	4. Build application by run "mvn package".
    5. To start the application, the class InternetxApplication in package de.internetx has to be right-clicked. In corresponding popmenu,
       you have to choose the option "Run internetx application" (Intellij idea).
       Alternativly you can run "java -jar '<target-folder/>'/testtask-0.0.1-SNAPSHOT.jar".
       In startup process 4 users and 4 roles are created for testing purposes.
    6. To test the different routes of the rest server, I have created a testtask.http file with different test cases. The file is in the same
           folder as pom.xml. 
   	
