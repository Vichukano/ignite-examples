# Apache Ignite thin client spring-data example

```shell
podman compose up -d
```

```shell
./gradlew clean build bootRun
```

execute DDL.sql to ignite cluster

```shell
curl -v -X POST http://localhost:8082/customer/JOHN-DOE
curl -v -X POST http://localhost:8082/order/JOHN-DOE/bobr/100500
curl -v -X GET http://localhost:8082/customer/JOHN-DOE
curl -v -X DELETE http://localhost:8082/order/JOHN-DOE/bobr
```
