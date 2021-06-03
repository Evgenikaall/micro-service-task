FROM maven:3.5-jdk-8 AS build

COPY src src
COPY pom.xml .

EXPOSE 8080

RUN mvn clean install

ENTRYPOINT ["mvn", "spring-boot:run"]