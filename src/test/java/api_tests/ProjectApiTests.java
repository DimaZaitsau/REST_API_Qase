package api_tests;

import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ProjectApiTests extends BaseApiTest    {

    private final static String PROJECT_TITLE = "Second project";
    private final static String PROJECT_CODE = "SP";

    @BeforeClass(alwaysRun = true)
    public void createProject() {
        given()
                .body(String.format("{\"code\": \"%s\", \"title\": \"%s\"}", PROJECT_CODE, PROJECT_TITLE))
                .when().log().all()
                .post("/project")
                .then().log().all();
    }

    @AfterClass(alwaysRun = true)
    public void deleteProject() {
        given()
                .pathParam("code", PROJECT_CODE)
                .body(String.format("{\"code\", \"%s\"}", PROJECT_CODE))
                .when().log().all()
                .delete("/project/{code}")
                .then().log().all();
    }

    @Test
    public void getAllProject() {
        given()
                .when().log().all()
                .get("/project")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true),
                        "result.total", equalTo(2));
    }
}
