# FleetManager
Fleet Manager Spring Boot

Swagger documentation: https://fleetmanager.aika.in/api/v1.0/


## Run with Maven
Tests:

    mvn test

Before run had to setup database and set environment variables:

    DATABASE_URL=jdbc:mysql://<hostname>:3306/fleetmanager
    DATABASE_USERNAME=<user>
    DATABASE_PASSWORD=<pass>

Run:

    mvn spring-boot:run

If you want change port, add 

    server.port=<newPort> 

to [src/main/resources/application.properties](src/main/resources/application.properties)

## Run with Docker

### With docker-command

This solutions also need database, make it before run with command:

    docker run --name my_fleetmaanger -p 8180:8080 -d aikain/fleetmanager:1.0 -e DATABASE_URL=jdbc:mysql://<hostname>:3306/fleetmanager -e DATABASE_USERNAME=<user> -e DATABASE_PASSWORD=<pass>

Just change port (8180) and envs to match your setup.

You can stop container:

    docker stop my_simplehttpserver
    
And start it again:

    docker start my_simplehttpserver

If you want remove container, stop it and then remove:

    docker stop my_simplehttpserver
    docker rm my_simplehttpserver

### With docker-compose

Change port (8180) in [docker-compose.yml](docker-compose.yml)-file to match your setup. If don't want clone repository and/or don't want build yourself, copy [docker-compose.yml](docker-compose.yml)-file and remove ```build: .``` and uncomment ```image: aikain/fleetmanager:1.0```.

Build and run with docker-compose:

    docker-compose up --build -d

Stop/Start/Restart:

    docker-compose stop
    docker-compose start
    docker-compose restart

Remove:

    docker-compose stop
    docker-compose rm
