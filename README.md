# A Spring Boot application representing a simple showcase for usage of OpenAPI, Feign and MockServer
This Maven project is just supposed to give an inspiration for using OpenAPI, Feign and MockServer.

It contains two Maven modules:

* **api** contains an OpenAPI spec "src/main/resources/specs/api.yaml".
* **impl** uses Maven plugins to copy the api to "src/main/resources", and generate model classes and Feign client

It is build with Java 1.8 and Apache Maven 3.6.0
