# Transfer Service

Transfer backend services and APIs.

## System Requirements

- Java 1.8+
- MongoDB

## API Guide

The [`api-guide.adoc`](src/main/asciidoc/api-guide.adoc) file is the documentation source file in 
[`AsciiDoc`](http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/) format. 
After starting the application, the rendered documentation, in `HTML` format, can be found at:

[`http://localhost:8182/api-guide.html`](http://localhost:8182/api-guide.html)

## Building

### Executing Unit Tests

    $ ./mvnw clean install

### Running our application in docker

    $ docker-compose up --build -d

