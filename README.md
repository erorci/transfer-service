# Transfer Service

Transfer backend services and APIs.

## System Requirements

- Java 1.8+
- MongoDB
- Docker
- Docker Compose

## API Guide

The [`api-guide.adoc`](src/main/asciidoc/api-guide.adoc) file is the documentation source file in 
[`AsciiDoc`](http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/) format. 
After starting the application, the rendered documentation, in `HTML` format, can be found at:

[`http://localhost:8182/api-guide.html`](http://localhost:8182/api-guide.html)

## Prepare Java (Spring Boot) application

Clone the following [`transfer-service`](https://github.com/erorci/transfer-service) and go to its directory.

### Building spring boot application

    $ ./mvnw clean install

Make sure target/transfer-service-0.0.1-SNAPSHOT.jar file is created. It is necessary to the next step.  

### Running our application in docker

    $ docker-compose up --build -d

### Checking container status docker

    $. docker ps 

    CONTAINER ID        IMAGE                         COMMAND                  CREATED             STATUS              PORTS                    NAMES
    4d267d714815        transfer-service_springboot   "java -Dspring.data.…"   11 minutes ago      Up 6 minutes        0.0.0.0:8182->8080/tcp   transfer-service
    32dfa7208425        mongo                         "docker-entrypoint.s…"   11 minutes ago      Up 6 minutes        27017/tcp                springboot-mongo

Once containers are up you can read the [`API doc`](http://localhost:8182/api-guide.html).