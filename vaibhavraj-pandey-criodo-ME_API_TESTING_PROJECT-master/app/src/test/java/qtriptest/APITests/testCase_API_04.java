package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {
    String emailId = "testUser@123";


    @Test(description = "Verify that a duplicate user account cannot be created on the Qtrip Website", groups = {"API Tests"})
    public void TestCase04(){
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

       JSONObject payloadsame = new JSONObject();
       payloadsame.put("email",emailId);
       payloadsame.put("password","abc1234");
       payloadsame.put("confirmpassword","abc1234");

       RequestSpecification request = RestAssured.given();
       request.contentType(ContentType.JSON)
       .body(payloadsame.toString());

      Response response= request.post("https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/register");
       response.then().assertThat().statusCode(400);

       JsonPath jsonPath = new JsonPath(response.asString());
       Assert.assertEquals(jsonPath.getString("message"), "Email already exists");

    }
}

  

