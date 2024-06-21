# SpringBoot learning project

## Topics Included while making the project
SpringBoot Starter, jdbcTemplate, CRUD, JPA, Hibernate DDL, REST API design, Docker, Postgres DB

## Run
- Includes a `Dockerfile` to create a container for java springboot service.
- Includes a `pg-docker-compose.yml` file for creating a postgres DB container.

1. Run the below command first to package the code into a Java *jar file* (this jar is sent to docker). 
The jar file for me was ~51MB.
```bash
./mvnw clean package
```

2. Create the docker image by running, replace `daynum` with your own username. It's just a tag for image.
```bash
 docker build . -t daynum/books
 
 # To verify image is generated
 docker image ls
```
3. Create the Postgres DB container image with `pg-docker-compose.yml`
```bash
docker compose up
```

>TODO
> Edit the compose file to build Jar container and start both, the PG container and the Jar container.