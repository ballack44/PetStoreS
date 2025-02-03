package com.example.sandbox.getPet;

import com.example.sandbox.Common;
import com.example.sandbox.util.Assertions;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.example.sandbox.util.constans.PetStatus;
import utils.report.TestListener;

import java.util.Map;
import java.util.TreeMap;

import static utils.Tags.GET;

@Listeners(TestListener.class)
public class GetPetByStatusTest extends Common {

    //OK
    @Test(groups = {GET},description ="Positive test GET /pet/findByStatus (Find pets by status)")
    public void testGetPetsByStatus_Success() {
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("status", PetStatus.AVAILABLE.getStatus());

        Response response = getUrl(findByStatus, queryParams);
        Assertions.assertReturnCode(response, 200);
        Assertions.assertResponseTime(response, 1500);

        String id = response.jsonPath().get("find{it.status.equals('available')}.id").toString();

        Response getResponse = getUrl(petById.replace("{petId}",id));
        Assertions.assertReturnCode(getResponse, 200);
        Assertions.assertResponseTime(getResponse, 1500);
    }

    //OK
    @Test(groups = {GET},description ="Negative test GET /pet/findByStatus (Find pets by status)")
    public void testGetPetsByStatus_Negative(){
        Map<String, String> queryParams = new TreeMap<>();
        //invalid status
        queryParams.put("status","XXX");

        Response response = getUrl(findByStatus, queryParams);
        Assertions.assertReturnCode(response, 200);
        Assertions.assertResponseTime(response, 1500);
        Assert.assertTrue(response.asString().equals("[]") || response.asString().isEmpty(),
                "Response should be empty for an invalid status");
    }

}
