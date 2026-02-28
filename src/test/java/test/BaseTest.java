package test;

import generators.UserTestData;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected UserTestData testData = new UserTestData();

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:8001";
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }
}
