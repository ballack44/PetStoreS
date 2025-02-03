package com.example.sandbox.postNewPet;

import com.example.sandbox.Common;
import com.example.sandbox.util.Assertions;
import com.example.sandbox.util.constans.TestData;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.report.TestListener;

import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;
import static utils.Tags.POST;

@Listeners(TestListener.class)
public class PostNewPetTest extends Common {

    //OK
    @Test(groups = {POST},description ="Positive test for POST /pet/")
    public void testPostNewPet_Success(){
        Response response = postUrl(newPet,createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(response, 200);
        Assertions.assertResponseTime(response, 1500);
    }

    //TODO the test shall not pass
    //REASON: the server shall not accept invalid pet data (missing mandatory values, unsupported status type etc.)
    //POTENTIAL BUG
    @Test(enabled = false, groups = {POST},description ="Negative test for POST /pet/")
    public void testPostNewPet_Negative(){
        Response response = postUrl(newPet, createJsonBody(TestData.invalidDummyPet));
        Assertions.assertReturnCode(response, 200);
        Assertions.assertResponseTime(response, 2000);
    }


}
