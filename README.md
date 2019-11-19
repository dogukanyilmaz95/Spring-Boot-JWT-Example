# Requirements
## Java - 1.8.x

## Maven - 3.x.x

## Mysql - 5.x.x

# Steps to Setup
## 1. Clone the application
```
 git clone https://github.com/dogukanyilmaz95/Spring-Boot-JWT-Example
```

## 2. Create Mysql database
```
docker pull mysql
```

### create database testdb
## 3. Change mysql username and password as per your installation

### open src/main/resources/application.properties

### change spring.datasource.username and spring.datasource.password as per your mysql installation

## 4. Build and run the app using maven
```
mvn spring-boot:run
```
# TEST
First User login with admin role
```
curl -d '{"username":"admin", "password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"}' -H "Content-Type: application/json" -X POST http://localhost:8080/login
```
Second User login with user role

```
curl -d '{"username":"user", "password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"}' -H "Content-Type: application/json" -X POST http://localhost:8080/login
```

Copy the Authorization value and enjoy it ;)
Admın has role the this service
```
curl -H "Content-Type: application/json" -H "Authorization":"COPY THE VALUE " -X GET http://localhost:8080/api/hello
```

User has role the this service
```
curl -H "Content-Type: application/json" -H "Authorization":"COPY THE VALUE " -X GET http://localhost:8080/api/jwt
```


