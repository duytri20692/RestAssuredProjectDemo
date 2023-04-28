import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@Epic("REST API Regression Testing using JUnit4")
@Feature("Verify CRUID Operations on Customer module")
public class CustomerAPITest {
    String BaseURL = null;
    Dotenv dotenv = Dotenv.load();

    @Test
    @Story("GET Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify get all customers")
    public void verifyGetAllCustomers() {
        BaseURL = "http://" + dotenv.get("API_URL") + "/customers";
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
                .body("responseData.customers.id[0]", is(1))
                .body("responseData.customers.firstName[0]", equalTo("Test"))
                .body("responseData.customers.lastName[0]", equalTo("User1"))
                .body("responseData.customers.email[0]", equalTo("TestUser1@testmail.com"))
                .body("responseData.customers.dateCreated[0]", equalTo("2023-04-26"));
    }

}
