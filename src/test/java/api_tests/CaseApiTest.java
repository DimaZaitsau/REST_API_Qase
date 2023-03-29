package api_tests;

import io.restassured.mapper.ObjectMapperType;
import models.Case;
import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CaseApiTest extends BaseApiTest    {

    private final static String PROJECT_TITLE = "Case project";
    private final static String PROJECT_CODE = "CP";
    private final static int CASE_ID = 1;

    @BeforeClass(alwaysRun = true)
    public void createProject() {
        given()
                .body(String.format("{\"code\": \"%s\", \"title\": \"%s\"}", PROJECT_CODE, PROJECT_TITLE))
                .when().log().all()
                .post("/project")
                .then().log().all();
    }

    @BeforeMethod(alwaysRun = true)
    public void crateCase() {
        Case casee = Case.builder().setTitle("Case").setStatus(0).setSeverity(1).setPriority(1).setType(2).setLayer(1)
                .setIsFlaky(0).setBehavior(1).setAutomationStatus(2).build();

        given()
                .pathParam("code", PROJECT_CODE)
                .body(casee, ObjectMapperType.GSON)
                .when().log().all()
                .post("/case/{code}")
                .then().log().all();
    }

    @AfterClass(alwaysRun = true)
    public void deleteProject() {
        given()
                .pathParam("code", PROJECT_CODE)
                .body(String.format("{\"code\": \"%s\"}", PROJECT_CODE))
                .when().log().all()
                .delete("/project/{code}")
                .then().log().all();
    }

    @Test
    public void getCase()   {
        given()
                .pathParam("code", PROJECT_CODE)
                .body(String.format("{\"code\": \"%s\"}", PROJECT_CODE))
                .when().log().all()
                .get("/case/{code}")
                .then().log().all();
    }

    @Test
    public void updateTestCase()    {
        Case casee = Case.builder().setTitle("New Title").setStatus(0).setSeverity(1).setPriority(1).setType(2)
                .setLayer(1).setIsFlaky(0).setBehavior(1).setAutomationStatus(2).build();

        given()
                .pathParam("code", PROJECT_CODE)
                .pathParam("id", CASE_ID)
                .body(casee, ObjectMapperType.GSON)
                .when().log().all()
                .patch("/case/{code}/{id}")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true));
    }

    @Test
    public void deleteCase()    {
        given()
                .pathParam("code", PROJECT_CODE)
                .pathParam("id", CASE_ID)
                .body(String.format("{\"code\": \"%s\", \"id\": \"%s\"}", PROJECT_CODE, CASE_ID))
                .when().log().all()
                .delete("/case/{code}/{id}")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true));
    }
}
