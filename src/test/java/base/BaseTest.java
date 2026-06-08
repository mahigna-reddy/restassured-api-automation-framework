package base;

import endpoints.Routes;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = Routes.BASE_URL;
    }
}