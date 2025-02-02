package com.example.sandbox.util;

import io.restassured.response.Response;
import org.testng.Assert;

public class Assertions {
    public static void assertReturnCode(Response response, int code){
        Assert.assertEquals(response.getStatusCode(), code, "Invalid response code");
    }

    //TODO: PROBLEM: response time was always higher than the required 500ms (as required in the HW)
    public static void assertResponseTime(Response response, int maxResponseTimeInMillis) {
        Assert.assertTrue(response.time() <= maxResponseTimeInMillis, String.format("Response took longer than %sms", maxResponseTimeInMillis));
    }
}
