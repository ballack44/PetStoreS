package com.example.sandbox;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import utils.report.ReportingFilter;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class Common extends Endpoints {

    protected ReportingFilter filter;

    @BeforeMethod(alwaysRun = true)
    public void baseBeforeMethod(ITestContext context) {filter = new ReportingFilter(context);}

    //----------------------------------GET----------------------------------
    public Response getUrl(String endpoint){

        return given()
                .relaxedHTTPSValidation()
                .and()
                .filter(filter)
                .when()
                .get(baseUrl+endpoint)
                .then()
                .extract().response();

    }
    public Response getUrl(String endpoint, Map<String, String> queryParam){

        return given()
                .relaxedHTTPSValidation()
                //.headers("correlationId","testCorrelid") //it is not neccessary
                //.cookie("session_id", "abc123") //it is not neccessary
                .queryParams(queryParam)
                .and()
                .filter(filter)
                .when()
                .get(baseUrl+endpoint)
                .then()
                .extract().response();

    }
    public Response getUrl(String endpoint, Map<String, String> headers,Map<String, String> queryParam){

        return given()
                .relaxedHTTPSValidation()
                .queryParams(queryParam)
                .headers(headers)
                .and()
                .filter(filter)
                .when()
                .get(baseUrl+endpoint)
                .then()
                .extract().response();

    }

    //----------------------------------POST----------------------------------
    public Response postUrl(String endpoint, String body){

        return given()
                .relaxedHTTPSValidation()
                .contentType("application/json; charset=UTF-8")
                .body(body)
                .and()
                .filter(filter)
                .when()
                .post(baseUrl+endpoint)
                .then()
                .extract().response();

    }

    public Response postUrl(String endpoint, Map<String, String> formParams){

        return given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .and()
                .filter(filter)
                .when()
                .post(baseUrl+endpoint)
                .then()
                .extract().response();

    }

   /* public Response postUrlWithImage(String endpoint, File image){

        return given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.MULTIPART)
                .multiPart("file", image)
                .and()
                .filter(filter)
                .when()
                .post(baseUrl+endpoint)
                .then()
                .extract().response();

    }*/

    public Response postUrlWithImage(String endpoint, File image, String additionalMetadata){
        return given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.MULTIPART)
                .multiPart("file", image)
                .formParam("additionalMetadata", additionalMetadata)
                .and()
                .filter(filter)
                .when()
                .post(baseUrl+endpoint)
                .then()
                .extract().response();
    }

    //----------------------------------PUT----------------------------------
    public Response putUrl(String endpoint, String body) {
        return given()
                .relaxedHTTPSValidation()
                .contentType("application/json; charset=UTF-8")
                .body(body)
                .and()
                .filter(filter)
                .when()
                .put(baseUrl + endpoint)
                .then()
                .extract().response();
    }

    //----------------------------------DELETE----------------------------------
    public Response deleteUrl(String endpoint){
        return given()
                .relaxedHTTPSValidation()
                .contentType("application/json; charset=UTF-8")
                .and()
                .filter(filter)
                .when()
                .delete(baseUrl + endpoint)
                .then()
                .extract().response();
    }

    // header parameter to an optional api_key attribute
    /*
    public Response deleteUrl(String endpoint, String header){
        return given()
                .relaxedHTTPSValidation()
                .header(new Header("api_key", header))
                .contentType("application/json; charset=UTF-8")
                .and()
                .filter(filter)
                .when()
                .delete(baseUrl + endpoint)
                .then()
                .extract().response();
    }
     */
}

