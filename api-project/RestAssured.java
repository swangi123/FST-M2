package GitHub_RestAssured_Project;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RestAssured {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String ssh_key;
    int ssh_id;


    @BeforeClass
    public void setUp() {

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();
    }

    @Test(priority=1)
        public void addNewkey(){
       String reqBody = "{\"title\": \"TestAPIKey\", \"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDRXdbRHv83XPbA29uZyeksV5keAhA/DrDfWmxN3J68zamGDnFjjHH5xeiVX4M9bIajkTbA3hzdKXV++BqUCnTd4s4CndOKynCRVC5XE2Vm9nDHTttCtC1EZU+b0IhA/RNUWfXHzyGCNgP8KFcnIoYova6tCHDl+yx3sZENZ+sE7l8w0ANcGoqe2sesLvQQv6tmblYtHwhwL2vHgJGVnp5grHK7ek1Me4DdY9ie4+OiA76mjF2lmKzEle7ohmDod95LkvBYfcis9swSrHeMp7iKQsJrY8fUas5P2ojgPDoIQ2hn0acaGFzF/co29hHqwwLcgWo/r/CTtvbKD8qUgnzluv3NhmlgiSeDFCPrGDTYmFuQYss6ZxDAZkvU0TFSRu6LjPWEE44LvRXksASY9x5IrczvSDtMuAtahMDQJ9g7U6zvfl0cHCqKkjo3dWlT1qLSyWi3spehGaZvlDbom/pgZAtFEvJWkql0ny/aNNnCndS/hgoTNwqyWQM5XWGdMhk=\"}";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody)
                .when().post("/user/keys");
        System.out.println(response.asPrettyString());
        ssh_id= response.path("id");
        System.out.println(ssh_id);
        response.then().extract().path("title").equals("TestAPIKey");
        response.then().statusCode(201);

        }

    @Test(priority=2)
    public void getkey(){
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("keyId", ssh_id) // Add path parameter
                .when().get("/user/keys/{keyId}");
        System.out.println(response.asPrettyString());
        response.then().extract().path("title").equals("TestAPIKey");
        response.then().statusCode(200);
    }

    @Test(priority=3)
    public void deletekey(){
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("keyId", ssh_id) // Add path parameter
                .when().delete("/user/keys/{keyId}"); // Send GET request


        System.out.println(response.asPrettyString());
        // Assertions
        response.then().statusCode(204);
    }
}
