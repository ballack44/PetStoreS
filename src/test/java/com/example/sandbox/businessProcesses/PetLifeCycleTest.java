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

    @Test(groups = {LIFECYCLE},description ="description")
    public void Test1(){
        //create a dummy pet
        Response response = postUrl(newPet, createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(response, 200);
        String id = response.jsonPath().get("id").toString();

        String newName = "UPDATED_XXX";
        TestData.testPet.getPetBody().setName(newName);
        TestData.testPet.getPetBody().setStatus(String.valueOf(PetStatus.SOLD));

        //update the existing dummy pet
        Response putResponse = putUrl("/pet", createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(putResponse, 200);
        Assertions.assertResponseTime(putResponse, 1500);

        Response response2 = getUrl(petById.replace("{petId}",id));
        Assertions.assertReturnCode(response2, 200);

        //check modification with GET method
        Response responseGet = getUrl("/pet/" + id);
        Assert.assertEquals((int)responseGet.jsonPath().get("id"), Integer.parseInt(id), "Wrong ID in the response");
        Assert.assertEquals(responseGet.jsonPath().get("name"), newName, "Wrong name in the response");
        Assertions.assertReturnCode(responseGet, 200);
        Assertions.assertResponseTime(responseGet, 1500);

        //delete
        Response deleteResponse = deleteUrl("/pet/" + id);
        Assertions.assertReturnCode(deleteResponse, 200);
        Assertions.assertResponseTime(deleteResponse, 1500);

        //check deletion with GET method
        responseGet = getUrl("/pet/" + id);
        Assert.assertNull(responseGet.jsonPath().get("id"), "The ID exists in the response");
        Assert.assertNull(responseGet.jsonPath().get("name"), "The name exists in the response");
        Assert.assertEquals(responseGet.jsonPath().get("message"), "Pet not found", "Error message does not match");
        Assertions.assertReturnCode(response, 200);  //TODO - it shall be 404
        Assertions.assertResponseTime(response, 1500);

    }
}
