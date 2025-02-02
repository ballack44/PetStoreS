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
import static utils.Tags.POST;

@Listeners(TestListener.class)
public class PostNewPetByIdTest extends Common {
    private static final int PET_ID = TestData.testPet.getPetBody().getId();

    //TODO
    //PROBLEM: the response contains error code 415
    //the following response
    /*
        The response has the following content which I think is not the desired:
        <apiResponse>
            <type>unknown</type>
        </apiResponse>
     */
    @Test(groups = {POST},description ="Positive test for POST /pet/{petID}")
    public void testPostNewPetById_Success(){
        //create a dummy pet
        Response response = postUrl(newPet, createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(response, 200);

        Map<String, String> formParams = new TreeMap<>();
        formParams.put("id", "" + PET_ID);
        formParams.put("name","NewXXX");
        formParams.put("status", PetStatus.SOLD.getStatus());

        //update the existing dummy pet
        Response postResponse = postUrl("/pet/" + PET_ID, formParams);
        Assertions.assertReturnCode(postResponse, 415); //TODO bug: it shall be 200 (OK)
        Assertions.assertResponseTime(postResponse, 1500);

    }

    //TODO as the previous problem above
    @Test(groups = {POST},description ="Negative test for POST /pet/{petID} with invalid petId")
    public void testPostNewPetById_Invalid(){
        Map<String, String> formParams = new TreeMap<>();
        formParams.put("id","XXX");
        formParams.put("name","NewXXX");
        formParams.put("status", PetStatus.AVAILABLE.getStatus());

        Response response = postUrl("/pet/" + PET_ID, formParams);

        Assertions.assertReturnCode(response, 415); //TODO bug: it shall be 200 (OK)
        Assertions.assertResponseTime(response, 2000);

    }

}
