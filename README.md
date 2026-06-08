Created a RestAssured API automation framework on GitHub as a continuation of my automation framework practice and knowledge-sharing projects.

This project focuses on API automation using Java, RestAssured, TestNG, Maven, and reusable payload classes with the Restful Booker public API.

The framework currently covers end-to-end API flows such as:

* Creating bookings using POST
* Retrieving booking details using GET
* Generating authentication tokens
* Updating bookings using PUT
* Partially updating bookings using PATCH
* Deleting bookings using DELETE
* Verifying deleted bookings return 404

As part of the framework design, I implemented:

* BaseTest for common API setup
* Routes class for reusable endpoint management
* Payload classes for request body creation
* TestNG assertions for status code and response body validation
* JsonPath extraction for dynamic response values
* Request and response logging for better debugging

The goal of this project is to show how API tests can be organized in a clean, reusable, and maintainable way instead of writing everything directly inside test methods.

GitHub Project: https://github.com/mahigna-reddy/restassured-api-automation-framework

#RestAssured #APITesting #Java #TestNG #Maven #SDET #QA #AutomationTesting #GitHub #SoftwareTesting
