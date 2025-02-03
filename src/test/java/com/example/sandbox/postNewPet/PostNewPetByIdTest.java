package com.example.sandbox.postNewPet;

import com.example.sandbox.Common;
import com.example.sandbox.util.Assertions;
import com.example.sandbox.util.constans.TestData;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.example.sandbox.util.constans.PetStatus;
import utils.report.TestListener;
import java.util.Map;
import java.util.TreeMap;
import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;
import static org.hamcrest.Matchers.equalTo;
import static utils.Tags.POST;

@Listeners(TestListener.class)
public class PostNewPetByIdTest extends Common {
    private static final String PET_ID = String.valueOf(TestData.testPet.getPetBody().getId());

    //OK
    @Test(groups = {POST},description ="Positive test for POST /pet/{petID}")
    public void testPostNewPetById_Success(){
        //create a dummy pet
        Response response = postUrl(newPet, createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(response, 200);

        String newName = "NewXXX";
        Map<String, String> formParams = new TreeMap<>();
        formParams.put("id", PET_ID);
        formParams.put("name", newName);
        formParams.put("status", PetStatus.SOLD.getStatus());

        //update the existing dummy pet

        Response postResponse = postUrl(petById.replace("{petId}", PET_ID), formParams);
        Assertions.assertReturnCode(postResponse, 200);
        Assertions.assertResponseTime(postResponse, 1500);

        //double check the update with GET (not required in theHW)
        Response getResponse = getUrl(petById.replace("{petId}", PET_ID));
        getResponse.then().body("id", equalTo(Integer.parseInt(PET_ID)));
        getResponse.then().body("name", equalTo(newName));

    }

    //OK
    @Test(groups = {POST},description ="Negative test for POST /pet/{petID} with invalid petId")
    public void testPostNewPetById_Invalid(){
        Map<String, String> formParams = new TreeMap<>();

        formParams.put("id","XXX");
        formParams.put("name","NewXXX");
        formParams.put("status", PetStatus.AVAILABLE.getStatus());

        //invalid ID
        Response response = postUrl("/pet/XXX", formParams);

        Assertions.assertReturnCode(response, 404);
        Assertions.assertResponseTime(response, 2000);

    }

}
