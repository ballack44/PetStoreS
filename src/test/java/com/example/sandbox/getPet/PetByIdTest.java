
package com.example.sandbox.getPet;

import com.example.sandbox.Common;
import com.example.sandbox.util.Assertions;
import com.example.sandbox.util.constans.TestData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.example.sandbox.util.constans.PetStatus;
import utils.report.TestListener;
import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;
import static utils.Tags.GET;
import static org.hamcrest.Matchers.equalTo;

@Listeners(TestListener.class)
public class PetByIdTest extends Common {
    //OK
    @Test(groups = {GET},description ="Positive test for GET /pet/{petId}")
    public void testGetPetById_Success() {
        int PET_ID = TestData.testPet.getPetBody().getId();
        //create a dummy pet
        Response response = postUrl(newPet, createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(response, 200);

        Response getResponse = getUrl("/pet/" + PET_ID);
        getResponse.then().body("id", equalTo(PET_ID));

        Assertions.assertReturnCode(getResponse, 200);
        Assertions.assertResponseTime(getResponse, 1500);

        Object id = getResponse.jsonPath().get("id");
        Object name = getResponse.jsonPath().get("name");
        Object status = getResponse.jsonPath().get("status");

        //Additional assertions to check mandatory fields
        Assert.assertNotNull(id, "ID is missing in response");
        Assert.assertNotNull(name, "Name is missing in response");
        Assert.assertNotNull(status, "Status is missing in response");
        //Additional assertions for Data Types
        Assert.assertTrue(id instanceof Integer, "ID is not of type Integer");
        Assert.assertTrue(name instanceof String, "Name is not of type String");
        Assert.assertTrue(status instanceof String, "Status is not of type String");

        // Ensure status is within valid values
        Assert.assertTrue(PetStatus.isValidStatus(status.toString()), "Invalid status value");
    }

    //OK
    @Test(groups = {GET},description ="Negative test for GET /pet/{petId} with an invalid petID")
    public void testGetPetById_NotFound() {
        final int PET_ID = Integer.MAX_VALUE;
        Response response = getUrl("/pet/" + PET_ID);

        Assert.assertTrue(response.asString().contains("Pet not found"), "Error message does not match");
        Assertions.assertReturnCode(response, 404);
        Assertions.assertResponseTime(response, 1500);
    }

}
