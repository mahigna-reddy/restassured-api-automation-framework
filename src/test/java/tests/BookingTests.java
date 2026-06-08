package tests;

import base.BaseTest;
import endpoints.Routes;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.Booking;
import payloads.BookingDates;
import payloads.AuthPayload;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingTests extends BaseTest {

    public String generateAuthToken() {

        AuthPayload authPayload = new AuthPayload("admin", "password123");

        Response response =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(authPayload)
                        .when()
                        .post(Routes.AUTH)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        Assert.assertEquals(response.statusCode(), 200);

        String token = response.jsonPath().getString("token");

        Assert.assertNotNull(token, "Auth token was not generated");

        return token;
    }
    @Test
    public void verifyCreateBookingSuccessfully() {

        BookingDates bookingDates = new BookingDates("2026-06-10", "2026-06-15");

        Booking booking = new Booking(
                "Mahigna",
                "Reddy",
                250,
                true,
                bookingDates,
                "Breakfast"
        );

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(booking)
                        .when()
                        .post(Routes.BOOKING)
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.statusCode(), 200);

        int bookingId = response.jsonPath().getInt("bookingid");
        String firstName = response.jsonPath().getString("booking.firstname");
        String lastName = response.jsonPath().getString("booking.lastname");

        Assert.assertTrue(bookingId > 0, "Booking ID was not generated");
        Assert.assertEquals(firstName, "Mahigna");
        Assert.assertEquals(lastName, "Reddy");
    }

    @Test
    public void verifyCreateAndGetBookingSuccessfully() {

        BookingDates bookingDates = new BookingDates("2026-07-01", "2026-07-05");

        Booking booking = new Booking(
                "John",
                "Smith",
                300,
                true,
                bookingDates,
                "Dinner"
        );

        Response createBookingResponse =
                given()
                        .header("Content-Type", "application/json")
                        .body(booking)
                        .when()
                        .post(Routes.BOOKING)
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(createBookingResponse.statusCode(), 200);

        int bookingId = createBookingResponse.jsonPath().getInt("bookingid");

        Assert.assertTrue(
                bookingId > 0,
                "Booking ID was not generated"
        );

        Response getBookingResponse =
                given()
                        .pathParam("id", bookingId)
                        .when()
                        .get(Routes.BOOKING_BY_ID)
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(getBookingResponse.statusCode(), 200);

        Assert.assertEquals(getBookingResponse.jsonPath().getString("firstname"), "John");
        Assert.assertEquals(getBookingResponse.jsonPath().getString("lastname"), "Smith");
        Assert.assertEquals(getBookingResponse.jsonPath().getInt("totalprice"), 300);
        Assert.assertEquals(getBookingResponse.jsonPath().getBoolean("depositpaid"), true);
        Assert.assertEquals(getBookingResponse.jsonPath().getString("bookingdates.checkin"), "2026-07-01");
        Assert.assertEquals(getBookingResponse.jsonPath().getString("bookingdates.checkout"), "2026-07-05");
        Assert.assertEquals(getBookingResponse.jsonPath().getString("additionalneeds"), "Dinner");
    }

    @Test
    public void verifyUpdateBookingSuccessfully() {

        BookingDates originalBookingDates = new BookingDates("2026-08-01", "2026-08-05");

        Booking originalBooking = new Booking(
                "David",
                "Miller",
                400,
                true,
                originalBookingDates,
                "Breakfast"
        );

        Response createBookingResponse =
                given()
                        .header("Content-Type", "application/json")
                        .body(originalBooking)
                        .when()
                        .post(Routes.BOOKING)
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(createBookingResponse.statusCode(), 200);

        int bookingId = createBookingResponse.jsonPath().getInt("bookingid");

        Assert.assertTrue(
                bookingId > 0,
                "Booking ID was not generated"
        );

        String token = generateAuthToken();

        BookingDates updatedBookingDates = new BookingDates("2026-09-10", "2026-09-15");

        Booking updatedBooking = new Booking(
                "David",
                "Wilson",
                550,
                false,
                updatedBookingDates,
                "Lunch"
        );

        Response updateBookingResponse =
                given()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .cookie("token", token)
                        .pathParam("id", bookingId)
                        .body(updatedBooking)
                        .when()
                        .put(Routes.BOOKING_BY_ID)
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(updateBookingResponse.statusCode(), 200);

        Assert.assertEquals(updateBookingResponse.jsonPath().getString("firstname"), "David");
        Assert.assertEquals(updateBookingResponse.jsonPath().getString("lastname"), "Wilson");
        Assert.assertEquals(updateBookingResponse.jsonPath().getInt("totalprice"), 550);
        Assert.assertEquals(updateBookingResponse.jsonPath().getBoolean("depositpaid"), false);
        Assert.assertEquals(updateBookingResponse.jsonPath().getString("bookingdates.checkin"), "2026-09-10");
        Assert.assertEquals(updateBookingResponse.jsonPath().getString("bookingdates.checkout"), "2026-09-15");
        Assert.assertEquals(updateBookingResponse.jsonPath().getString("additionalneeds"), "Lunch");
    }

    @Test
    public void verifyPartialUpdateBookingSuccessfully() {

        BookingDates bookingDates = new BookingDates("2026-10-01", "2026-10-05");

        Booking booking = new Booking(
                "Michael",
                "Brown",
                450,
                true,
                bookingDates,
                "Breakfast"
        );

        Response createBookingResponse =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(booking)
                        .when()
                        .post(Routes.BOOKING)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        Assert.assertEquals(createBookingResponse.statusCode(), 200);

        int bookingId = createBookingResponse.jsonPath().getInt("bookingid");

        Assert.assertTrue(
                bookingId > 0,
                "Booking ID was not generated"
        );

        String token = generateAuthToken();

        Map<String, Object> partialUpdatePayload = new HashMap<>();
        partialUpdatePayload.put("lastname", "Taylor");
        partialUpdatePayload.put("additionalneeds", "Late Checkout");

        Response patchBookingResponse =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .cookie("token", token)
                        .pathParam("id", bookingId)
                        .body(partialUpdatePayload)
                        .when()
                        .patch(Routes.BOOKING_BY_ID)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        Assert.assertEquals(patchBookingResponse.statusCode(), 200);

        Assert.assertEquals(patchBookingResponse.jsonPath().getString("firstname"), "Michael");
        Assert.assertEquals(patchBookingResponse.jsonPath().getString("lastname"), "Taylor");
        Assert.assertEquals(patchBookingResponse.jsonPath().getInt("totalprice"), 450);
        Assert.assertEquals(patchBookingResponse.jsonPath().getBoolean("depositpaid"), true);
        Assert.assertEquals(patchBookingResponse.jsonPath().getString("bookingdates.checkin"), "2026-10-01");
        Assert.assertEquals(patchBookingResponse.jsonPath().getString("bookingdates.checkout"), "2026-10-05");
        Assert.assertEquals(patchBookingResponse.jsonPath().getString("additionalneeds"), "Late Checkout");
    }

    @Test
    public void verifyDeleteBookingSuccessfully() {

        BookingDates bookingDates = new BookingDates("2026-11-01", "2026-11-05");

        Booking booking = new Booking(
                "Sophia",
                "Anderson",
                500,
                true,
                bookingDates,
                "Breakfast"
        );

        Response createBookingResponse =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(booking)
                        .when()
                        .post(Routes.BOOKING)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        Assert.assertEquals(createBookingResponse.statusCode(), 200);

        int bookingId = createBookingResponse.jsonPath().getInt("bookingid");

        Assert.assertTrue(
                bookingId > 0,
                "Booking ID was not generated"
        );

        String token = generateAuthToken();

        Response deleteBookingResponse =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .cookie("token", token)
                        .pathParam("id", bookingId)
                        .when()
                        .delete(Routes.BOOKING_BY_ID)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        Assert.assertEquals(deleteBookingResponse.statusCode(), 201);

        Response getDeletedBookingResponse =
                given()
                        .log().all()
                        .pathParam("id", bookingId)
                        .when()
                        .get(Routes.BOOKING_BY_ID)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        Assert.assertEquals(
                getDeletedBookingResponse.statusCode(),
                404,
                "Deleted booking is still available"
        );
    }
}