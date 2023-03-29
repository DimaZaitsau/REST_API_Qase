package api_tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.protocol.HTTP;
import org.testng.annotations.BeforeTest;
import utils.PropertyReader;
import static io.restassured.RestAssured.*;

public abstract class BaseApiTest {

    private final static String TOKEN = PropertyReader.getProperty("api_access_token");
    private final static String BASE_URL = PropertyReader.getProperty("api_base_url");

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.requestSpecification = given()
                .header("Token", TOKEN)
                .header(HTTP.CONTENT_TYPE, ContentType.JSON);
    }
}
