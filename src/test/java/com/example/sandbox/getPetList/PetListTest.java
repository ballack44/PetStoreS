package com.example.sandbox.getPetList;

import com.example.sandbox.Common;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.report.TestListener;

import java.util.Map;
import java.util.TreeMap;

import static utils.Tags.SMOKE;


@Listeners(TestListener.class)
//TODO - This is out of scope of the homework, but did not want to delete the legacy test class (it can be useful in the future or a smoke test)
public class PetListTest extends Common {

    @Test(groups = {SMOKE},description ="description")
    public void Test1(){
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("status","available");
        queryParams.put("asd","asd");
        queryParams.put("maki","kakadu");

        Response response = getUrl(findByStatus, queryParams);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(groups = {SMOKE},description ="description")
    public void Test2(){
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("status","available");
        Map<String, String> headers = new TreeMap<>();
        headers.put("Mandatoyheader","BFG");

        Response response = getUrl(findByStatus, headers, queryParams);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");

    }
}
