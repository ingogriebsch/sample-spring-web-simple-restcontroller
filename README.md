# Spring Web simple REST controller sample
[![Actions Status](https://github.com/ingogriebsch/sample-spring-web-simple-restcontroller/workflows/build/badge.svg)](https://github.com/ingogriebsch/sample-spring-web-simple-restcontroller/actions)
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=de.ingogriebsch.samples%3Asample-spring-web-simple-restcontroller)](https://sonarcloud.io/dashboard?id=de.ingogriebsch.samples%3Asample-spring-web-simple-restcontroller)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This sample shows how to implement a simple REST controller with Spring Web (and Spring Boot).

## How to build and run
If you want to see the implementation in action, simply follow these steps:

*   First, make sure that you have Java 8 or later installed and an established Internet connection.
*   Then, clone this Git repository an `cd` into the project folder. 
*   Now invoke `./mvnw spring-boot:run` and wait a moment.

After the project is built and the Spring Boot service is running you can hit the following urls to act with the service:

*   [http://localhost:8080](http://localhost:8080) allows to access this site.
*   [http://localhost:8080/persons](http://localhost:8080/persons) allows to access the person resources available through the service.

Have a look into the implementation to understand which endpoints are available in addition.

## Most important annotations and classes
A collection of the most important annotations and classes that are necessary to implement this use case. 

*   [@GetMapping](https://docs.spring.io/spring-framework/docs/5.3.2/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html)
*   [@PostMapping](https://docs.spring.io/spring-framework/docs/5.3.2/javadoc-api/org/springframework/web/bind/annotation/PostMapping.html)
*   [@DeleteMapping](https://docs.spring.io/spring-framework/docs/5.3.2/javadoc-api/org/springframework/web/bind/annotation/DeleteMapping.html)
*   [@RequestMapping](https://docs.spring.io/spring-framework/docs/5.3.2/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html)
*   [@RestController](https://docs.spring.io/spring-framework/docs/5.3.2/javadoc-api/org/springframework/web/bind/annotation/RestController.html)
*   [@WebMvcTest](https://docs.spring.io/spring-boot/docs/2.4.1/api/org/springframework/boot/test/autoconfigure/web/servlet/WebMvcTest.html)

## Used frameworks
A collection of the mainly used frameworks in this project. 
There are more, but they are not that present inside the main use case, therefore they are not listed here.

*   [Spring Web](https://docs.spring.io/spring-framework/docs/5.3.2/spring-framework-reference/web.html#spring-web)
*   [Spring Boot](https://docs.spring.io/spring-boot/docs/2.4.1/reference/htmlsingle/)

## Additional guides
The following guides illustrate how to implement this and related use cases.

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

## License
This code is open source software licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0.html).
