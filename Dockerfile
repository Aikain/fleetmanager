FROM maven:3-jdk-10

WORKDIR /code

ADD pom.xml /code/pom.xml
ADD src /code/src

RUN mvn test

EXPOSE 80
CMD ["mvn", "spring-boot:run"]
