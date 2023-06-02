# spring-securiry-integrationTests
Make a Rest API for the book entity + validation
Add API swagger
Add authentication and create a new user
Authentication via JWT
Add the possibility of creating a new user (registration)
In swagger-ui, add the ability to transfer the jwt token to have access to secure androids in swagger
Cover with test. Achieve at least 80% test coverage.

_________________________in progress_________________________

Write CatService that makes REST call to https://catfact.ninja/fact
Write @SpringBootTest integration test on this service
Use Wiremock to mock catfact webserver
Use @TestProperty to change catfact’s URL from https://catfact.ninja to localhost:XXXX where XXXX - your Wiremock port
