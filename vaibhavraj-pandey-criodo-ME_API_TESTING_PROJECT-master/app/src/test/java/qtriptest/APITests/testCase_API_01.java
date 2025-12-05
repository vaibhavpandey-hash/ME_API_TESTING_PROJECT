package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.UUID;



public class testCase_API_01 {
    String emailId = "testUser@123";
    public String token;
    public String userId;

    @Test(description = "Verify that a new user can be registered and login using APIs of QTrip",groups = {"API Tests"})
    public void TestCase01(){
        emailId = emailId + UUID.randomUUID();
     // 1. Use the register API to register a new user
    JSONObject payload = new JSONObject();
    payload.put("email",emailId);
    payload.put("password","abc1234");
    payload.put("confirmpassword","abc1234");

    RestAssured.given()
    .contentType(ContentType.JSON)
    .body(payload.toString())
    .when().post("https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/register")
    .then().assertThat().statusCode(201);

    // 2. Use the Login API to login using the registered user
    JSONObject payloadLogin =new JSONObject();
    payloadLogin.put("email",emailId);
    payloadLogin.put("password","abc1234");

    RequestSpecification request = RestAssured.given();
    request.contentType(ContentType.JSON);
    request.body(payloadLogin.toString());

    Response response = request.post("https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/login");

    JsonPath jsonpath = new JsonPath(response.asString());

    // 3. Validate that the login was successful
    Assert.assertTrue(jsonpath.getBoolean("success"));
    // 4. Verify that the token and user id is returned for login
    Assert.assertNotNull(jsonpath.getString("data.token"));
    token = jsonpath.getString("data.token");
    Assert.assertNotNull(jsonpath.getString("data.id"));
    userId = jsonpath.getString("data.id");
    }
   
}
