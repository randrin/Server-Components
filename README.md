---
**** Set Up Spring Boot Project ****
---
1) Create Spring Boot project in the IDE
2) When creating, Add the basis dependences: Jpa, Security, MySql, Lombok, Spring Web [see the pom.xml]
3) Create some packages: configuration, controller, model, service, util

---
**** Model Class NbpUser ****
---
1) Create the NbpUser class in the model package
2) Add the some basic fields: id, username, password, email, etc...
3) Add annotations to generate getter and setter, constructors, entity table

---
**** Actuator for monitoring service ****
---
1) Add the dependency actuator starter
2) Restart the application and go to loacalhost:port/actuactor
3) Configure the application.yaml for the Business Logic

---
**** Registration Microservice in Admin Server ****
---
1) Add the dependency admin client
2) Configure the application.properties (application.yaml) registering with the url of admin server

---
**** Notifications Microservice in Admin Server ****
---
1) Add the dependency in admin server [spring-boot-starter-mail]
2) Configure the application.properties (application.yaml) registering with the port, host, domain etc.. of the server

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