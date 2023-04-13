# Quarkus PetStore

[Download the code from GitHub](https://github.com/ligangty/quarkus-store)

## Prerequisite for building
1. jdk11
2. mvn 3.6.2+

## Prerequisite for debugging in local
1. docker 20+
2. docker-compose 1.20+


## Compile and package

`mvn clean package`

## Execute the sample

* First, start up the postgresql and keycloak. You can use [docker-compose](https://github.com/docker/compose) to start testing ones in compose/
* Execute [create_db.sql](./quarkus-store-api/src/main/db/create_db.sql) to setup database
* Login [keycloak admin console](http://localhost:8180/) with admin/admin, and import
[qstore realm](./compose/keycloak/realm-qstore.json)
* Start quarkus-store-api: in quarkus-store-api folder, execute `mvn quarkus:dev`
* Start quarkus-store-ui: in quarkus-store-ui folder, execute `mvn quarkus:dev`
* Access http://localhost:8081 to see the web page


