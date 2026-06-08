# RestAssured API Automation Framework

This project is a RestAssured API automation framework built using Java, TestNG, Maven, and reusable payload classes. It automates API test scenarios on the Restful Booker public API.

The goal of this project is to demonstrate how API automation can be structured using reusable request payloads, endpoint routes, base setup, and TestNG assertions.

## Application Under Test

Restful Booker API: https://restful-booker.herokuapp.com/apidoc/index.html

## Tech Stack

* Java
* RestAssured
* TestNG
* Maven
* Jackson Databind
* JSON Schema Validator
* IntelliJ IDEA
* GitHub

## Framework Design

This project follows a clean API automation structure:

* **BaseTest**: Handles common RestAssured base URI setup.
* **Routes**: Stores reusable API endpoints such as auth, booking, and booking by ID.
* **Payload Classes**: Uses Java classes to create reusable request bodies.
* **TestNG**: Used for test execution and assertions.
* **RestAssured**: Used for sending API requests and validating responses.
* **Maven**: Used for dependency management and test execution.

## Automated API Test Coverage

The framework currently covers key Restful Booker API flows:

* Create booking using POST API
* Create booking and retrieve it using GET API
* Generate authentication token
* Fully update booking using PUT API
* Partially update booking using PATCH API
* Delete booking using DELETE API
* Verify deleted booking returns 404 using GET API

## Project Structure

```text
restassured-api-automation-framework
├── src
│   └── test
│       └── java
│           ├── base
│           │   └── BaseTest.java
│           ├── endpoints
│           │   └── Routes.java
│           ├── payloads
│           │   ├── AuthPayload.java
│           │   ├── Booking.java
│           │   └── BookingDates.java
│           ├── tests
│           │   └── BookingTests.java
│           └── utils
├── pom.xml
└── README.md
```

## Key Features

* API automation using RestAssured
* TestNG-based test execution
* Maven-based dependency management
* Reusable route management using `Routes`
* Reusable request bodies using payload classes
* Auth token generation for protected APIs
* CRUD API validation: POST, GET, PUT, PATCH, DELETE
* Status code validation
* Response body validation using JsonPath
* Request and response logging using RestAssured logs

## API Scenarios Covered

### Create Booking

Creates a new booking using POST `/booking` and validates booking ID, first name, and last name.

### Get Booking by ID

Creates a booking, extracts the booking ID, retrieves the booking using GET `/booking/{id}`, and validates the saved booking details.

### Update Booking

Creates a booking, generates an auth token, updates the full booking using PUT `/booking/{id}`, and validates the updated response.

### Partial Update Booking

Creates a booking, generates an auth token, partially updates selected fields using PATCH `/booking/{id}`, and validates that updated and unchanged fields are handled correctly.

### Delete Booking

Creates a booking, generates an auth token, deletes the booking using DELETE `/booking/{id}`, and verifies the deleted booking returns 404 when retrieved again.

## How to Run Tests

Clone the repository:

```bash
git clone https://github.com/mahigna-reddy/restassured-api-automation-framework.git
```

Go to the project folder:

```bash
cd restassured-api-automation-framework
```

Run tests using Maven:

```bash
mvn clean test
```

## What I Practiced

Through this project, I practiced:

* Creating API automation tests using RestAssured
* Structuring an API automation framework
* Managing reusable API routes
* Creating reusable request payload classes
* Sending POST, GET, PUT, PATCH, and DELETE requests
* Generating and using authentication tokens
* Validating status codes and response bodies
* Extracting response values using JsonPath
* Chaining API flows such as create, retrieve, update, and delete
* Running API tests using TestNG and Maven

## Future Enhancements

Planned improvements for this framework:

* Add `config.properties` for environment-based configuration
* Add reusable API utility methods
* Add response schema validation
* Add negative API test scenarios
* Add data-driven API tests
* Add Extent Reports or Allure Reports
* Add GitHub Actions for CI execution

## Author

Mahigna Reddy
