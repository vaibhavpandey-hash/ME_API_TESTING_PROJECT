package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
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

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

public class testCase_API_02 {
    @Test(description = "Verify that the search City API Returns the correct number of results",groups = {"API Tests"})
    public void TestCase02(){
    // 1. Search for "beng" using the cities search API

    RequestSpecification request = RestAssured.given();
    request.queryParam("q","beng");
    //request.log().all();
    Response response = request.get("https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/cities");
    //1.After successful search, the status code must be 200
    response.then().assertThat().statusCode(200);

    // 2. Verify the count of results being returned

    //2. The Result should be an array of length 1
    response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonSchemas/TestCase_02_Schema.json")));

    //3. The Description should contain "100+ Places"
    //Validate Schema
    JsonPath jsonpath = new JsonPath(response.asString());
    

    Assert.assertEquals(jsonpath.getString("description"), "[100+ Places]");
    //response.log().all();
    }

}
