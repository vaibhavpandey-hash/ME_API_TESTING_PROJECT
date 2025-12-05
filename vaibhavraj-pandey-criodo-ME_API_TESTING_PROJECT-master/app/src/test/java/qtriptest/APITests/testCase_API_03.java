package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

public class testCase_API_03 {
    String emailId = "testUser@123";
    public String token;
    public String userId;

    @Test(description = "Verify that a reservation can be made using the QTrip API" , groups = {"API Tests"})
    public void TestCase03(){
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

   //     2. Perform a booking using a post call
   JSONObject payloadResv = new JSONObject();

   payloadResv.put("userId",userId);
   payloadResv.put("name","testuser");
   payloadResv.put("date","2025-29-12");
   payloadResv.put("person","1");
   payloadResv.put("adventure","2447910730");

   RequestSpecification requestresv = RestAssured.given();
   requestresv.contentType(ContentType.JSON);
   requestresv.body(payloadResv.toString());
   requestresv.header("Authorization","Bearer "+token);


   Response responseResv = requestresv.post("https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/reservations/new");

   responseResv.then().assertThat().statusCode(200);
  // 3. Ensure that the booking goes fine
    RestAssured.given()
    .queryParam("id",userId)
    .header("Authorization","Bearer "+token)
    .when().get("https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/reservations/")
    .then().assertThat().statusCode(200);
    }
}
