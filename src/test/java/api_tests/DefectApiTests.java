package api_tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.mapper.ObjectMapperType;
import models.Defect;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class DefectApiTests extends BaseApiTest    {

    private final static String PROJECT_TITLE = "New project";
    private final static String PROJECT_CODE = "QWERTY";
    private final static int DEFECT_ID = 1;

    @BeforeClass(alwaysRun = true)
    public void createProject() {
        given()
                .body(String.format("{\"code\": \"%s\", \"title\": \"%s\"}", PROJECT_CODE, PROJECT_TITLE))
                .when().log().all()
                .post("/project")
                .then().log().all();
    }

    @BeforeMethod(alwaysRun = true)
    public void newDefect()  {
        Defect defect = Defect.builder()
                .setTitle("Bad working service")
                .setActualResult("Is not working")
                .setSeverity(3).build();

        given()
                .pathParam("code", PROJECT_CODE)
                .body(defect, ObjectMapperType.GSON)
                .when().log().all()
                .post("/defect/{code}")
                .then().log().all();
    }

    @AfterClass(alwaysRun = true)
    public void deleteProject()  {
        given()
                .pathParam("code", PROJECT_CODE)
                .body(String.format("{\"code\": \"%s\"}", PROJECT_CODE))
                .when().log().all()
                .delete("/project/{code}")
                .then().log().all();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void createNewDefect()   {
        Defect defect = Defect.builder()
                .setTitle("Hello, I'm new defect")
                .setActualResult("I'm not working")
                .setSeverity(3).build();

        given()
                .pathParam("code", PROJECT_CODE)
                .body(defect, ObjectMapperType.GSON)
                .when().log().all()
                .post("/project/{code}")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true));
    }

    @Severity(SeverityLevel.MINOR)
    @Test
    public void getSpecificDefect() {
        given()
                .pathParam("code", PROJECT_CODE)
                .pathParam("id", DEFECT_ID)
                .when().log().all()
                .get("/defect/{code}/{id}")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true),
                        "result.id", equalTo(1),
                        "result.title", equalTo("Hello, I'm new defect"),
                        "result.actual_result", equalTo("I'm not working"),
                        "result.severity", equalTo("major"));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void updateDefectFile()  {
        Defect defect = Defect.builder()
                .setTitle("Defect title")
                .setActualResult("Defect not working")
                .setSeverity(2).build();

        given()
                .pathParam("code", PROJECT_CODE)
                .pathParam("id", DEFECT_ID)
                .body(defect, ObjectMapperType.GSON)
                .when().log().all()
                .patch("/defect/{code}/{id}")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void deleteDefect()   {
        Defect defect = Defect.builder()
                .setTitle("Defect")
                .setActualResult("Don't work")
                .setSeverity(3).build();

        String defectId = given()
                .pathParam("code", PROJECT_CODE)
                .body(defect, ObjectMapperType.GSON)
                .when().log().all()
                .post("/defect/{code}")
                .then().log().all()
                .extract().path("result.id").toString();

        int defectIdToInt =Integer.parseInt(defectId);

        given()
                .pathParam("code", PROJECT_CODE)
                .pathParam("id", defectIdToInt)
                .when().log().all()
                .delete("/defect/{code}/{id}")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true));

    }
}
