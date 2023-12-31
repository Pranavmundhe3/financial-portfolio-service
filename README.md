# financial-portfolio-service
Java, spring boot based project to display monthly and cumulative balance as response using REST API.
==========================

**Tech stack**-

Java 11, Spring boot 2.7, REST APIs, Microservice, SLF4J Logging, Maven

==========================


**Coding standards and style**-

1. Following layered architechture to communicate data using web API, 
Controller layer- API call first reach here and calls particular handler method as defined in GetMapping.
Service layer- This is where actual business logic resides.

2. Inter service/Microservice communication is handled using RestTemplate in Spring boot, this way I can access data sent by another service. I have received Transactions list by calling another team API susing RestTemplate.

3. Using Stream API to iterate over collection objects utilizing functional programming aspect

4. Modularized code with no duplications, using private methods and Serializable classes 

5. Accessing 3rd Party external host URL from application.properties file using @Value(), this way if host URL at all changes then that change will be limited to one and only one place
 
==========================


**Presumptions**-

1. User will give input "year" and balance details will be returned as a response for that year
2. Transaction can be either credit or debit type. This is to avoid representing amount in negative digit format.
3. Amount is depicted using BigDecimal literal considering wide/large range of values it can have.

==========================

**Answers to questions asked in the assignment/task email**-

1. How to design your application so that it is testable?
-> I used Java 11 with Spring boot framework to design service logic of computing balance details. Java supports different types of testing using frameworks, libraries

Unit testing-
Java offers support for JUnit framework to test service logic, I can combine JUnit framework with Mockito to mock actual service calls and return mock reponse instead of actual response, finally I will assert mocked reponse to exact expected response.

Automation testing-
Automated test cases can be written using frameworks like Robot (Java supported) which can directly call our application exposed endpoints and assert it with expected response.

Integration testing, Manual testing, Smoke testing-
Using application UI built with the help of some frontend framework/libraries(like Angular or React) we can call this exposed REST API and display returned reponse. Manual triggering of this request using some button/reload should return correct reponse all the time.

This way I can ensure my aplication is less prone to bugs.




2. If the application must be deployed to a server in remote location, how would you do it?

Considering this is the only application without any other services, we can deploy this over AWS Elastic Beanstalk service. This is quick way to make application live ready to use with minimal configuration of server settings. It automatically scale up the application on EC2 instance having OpenJDK platform, we just need to provide jar file to it. 
A application JAR file can be created using maven clean build. It will be generate JAR file in /target folder of project directory. An EC2 instance will provide URL. This URL is point of contact to backend host/server which is called in UI framework.


==========================

**File structure**-

Find service logic here-
financial-portfolio-service/src/main/java/com/info/financialportfolioservice/service/impl/BalanceServiceImpl.java

Find API here-
financial-portfolio-service/src/main/java/com/info/financialportfolioservice/controller/BalanceController.java





 
