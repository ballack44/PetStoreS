package com.example.sandbox.businessProcesses;

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
import static utils.Tags.LIFECYCLE;

@Listeners(TestListener.class)
public class PetLifeCycleTest extends Common {

    @Test(groups = {LIFECYCLE},description ="Lifecycle test for pet (create,update,getById,delete,getById)")
    public void petLifeCycleTest(){
        //create a dummy pet
        Response response = postUrl(newPet, createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(response, 200);
        String id = response.jsonPath().get("id").toString();

        String newName = "UPDATED_XXX";
        TestData.testPet.getPetBody().setName(newName);
        TestData.testPet.getPetBody().setStatus(String.valueOf(PetStatus.SOLD));

        //update the existing dummy pet
        // (just for the sake of interest use PUT instead of POST /pet/{testID}
        Response putResponse = putUrl(newPet, createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(putResponse, 200);
        Assertions.assertResponseTime(putResponse, 1500);

        //check modification with GET method
        Response responseGet = getUrl(petById.replace("{petId}",id));
        Assert.assertEquals((int)responseGet.jsonPath().get("id"), Integer.parseInt(id), "Wrong ID in the response");
        Assert.assertEquals(responseGet.jsonPath().get("name"), newName, "Wrong name in the response");
        Assertions.assertReturnCode(responseGet, 200);
        Assertions.assertResponseTime(responseGet, 1500);

        //delete
        Response deleteResponse = deleteUrl(petById.replace("{petId}",id));
        Assertions.assertReturnCode(deleteResponse, 200);
        Assertions.assertResponseTime(deleteResponse, 1500);

        //check deletion with GET method
        responseGet = getUrl(petById.replace("{petId}",id));
        Assert.assertNull(responseGet.jsonPath().get("id"), "The ID exists in the response");
        Assert.assertNull(responseGet.jsonPath().get("name"), "The name exists in the response");
        Assert.assertEquals(responseGet.jsonPath().get("message"), "Pet not found", "Error message does not match");
        Assertions.assertReturnCode(responseGet, 404);
        Assertions.assertResponseTime(responseGet, 1500);

    }
}
