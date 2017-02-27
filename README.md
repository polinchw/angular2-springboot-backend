## springboot-data-rest
Simple Spring Boot project that uses JPA and Rest.
Provides Docker support.

### To run this program on the command line use these commands

#### Basic start up
java -jar -springboot-data-rest.jar

#### Using the application-prod.properties
java -jar -Dspring.profiles.active=prod springboot-data-rest.jar

#### Using the application-prod.properties and overriding the host
java -jar -Dspring.profiles.active=prod -Dhost=gmail.com springboot-data-rest.jar

### To run this program on Docker run these commands

#### Maven command to build the Docker image
mvn package docker:build
#### Run the Docker image on Docker
docker run -p 8080:8080 -t polinchw/springboot-data-rest
#### Run the Docker image on Docker and overriding a config parameter 
docker run -e "host=docker" -p 8080:8080 -t polinchw/springboot-data-rest
#### Run the Docker image on Docker using the application-prop.properties config file 
docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 -t polinchw/springboot-data-rest
#### Run the Docker image on Docker using the application-prop.properties config file and overriding the host property
docker run -e "SPRING_PROFILES_ACTIVE=prod" -e "host=docker" -p 8080:8080 -t polinchw/springboot-data-rest
#### To connect to the bash shell of the running container
docker exec -it "id of running container" bash

/home/polinchakb/order-update-processed-outbox will have files of order updates