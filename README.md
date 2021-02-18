---
**** Set Up Spring Boot Project ****
---
1) Create Spring Boot project in the IDE
2) When creating, Add the basic dependencies: Jpa, Security, MySql, Lombok, Spring Web [see the pom.xml]
3) Create some packages: configuration, repository, controller, model, service, util, filter, etc...

---
**** Configuration application.yaml  ****
---
1) Go to the resources folder
2) Create the application.yaml or application.yml [In my case, i rename the application.properties created by Spring boot automatically]
3) Make some configurations, like set up database, server port, etc... [Some configurations will be added later]
4) Create the profile active prod and the application-prod.yaml [This configuration is the process to deploy the application in AWS Elastic Beanstalk. When working in local, delete the prod and make it empty]

---
**** Model Class NbpUser ****
---
1) Create the NbpUser class in the model package
2) Add some basic fields: id, userName, password, email, etc...
3) Add annotations to generate getters and setters [@Data -> Lombok], constructors [@AllArgsConstructor, @NoArgsConstructor -> Lombok], entity table

---
**** Security Configuration ****
---
1) Create the NbpSecurityConfiguration class in the configuration package
2) Add the configuration annotations [@Configuration, @EnableWebSecurity] 
3) Override the configure method [This method call the authentification security, but we need to persist with our service NbpUserDetailsService in agreement with the Business Logic]

---
**** Service NbpUserDetailsService ****
---
1) Create the service NbpUserDetailsService and implements the UserDetailsService
2) Override the loadUserByUsername method [This method call the model user and get the informations of user in database]

---
**** Controller NbpUserController ****
---
1) Create the controller NbpUserController in the controller package
2) Implements some basic endpoints to call the data in database

---
**** Data Persistence and Clustering with Hazelcast in SBA Server ****
---
1) Add the dependency in admin server [hazelcast]
2) Create the configuration class: to to the link: https://codecentric.github.io/spring-boot-admin/current/#clustering-support
3) Create more instances of server and stop/start one any time. the data persistence don't loss

---
**** Swagger Implementation ****
---
1) Add the dependency in pom.xml [io.springfox]
2) Configure the swagger bean [SwaggerConfig.class]