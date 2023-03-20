import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@Epic("REST API Regression Testing using JUnit4")
@Feature("Verify CRUID Operations on Loan module")
public class LoanAPITest {
    String BaseURL = "http://localhost:8080/loans";

    @Test
    @Story("GET Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify get all loan")
    public void verifyGetAllLoan() {

        // Given
        given()
                .filter(new AllureRestAssured())
        // WHEN
        .when()
                .get(BaseURL)
        // THEN
        .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                // To verify booking id at index 2
                .body("resp_data.id[0]", is(3))
                .body("resp_data.name[0]", equalTo("mortgage"))
                .body("resp_data.amount[0]", is(33.33f));
    }

    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify the creation of a new loan")
    public void createLoan() {
        JSONObject data = new JSONObject();

        data.put("id", "123");
        data.put("name", "Tri Nguyen");
        data.put("amount", "12.34");

        // GIVEN
        given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(data.toString())

        // WHEN
        .when()
                .post(BaseURL)

        // THEN
        .then()
                .statusCode(201)
                .body("loan.id", isA(Integer.class))
                .body("loan.name", equalTo("Tri Nguyen"))
                .body("loan.amount", is(12.34f));
    }
}
