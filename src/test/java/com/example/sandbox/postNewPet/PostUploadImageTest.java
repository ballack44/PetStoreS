package com.example.sandbox.postNewPet;

import com.example.sandbox.Common;
import com.example.sandbox.util.Assertions;
import com.example.sandbox.util.constans.TestData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.report.TestListener;

import java.io.File;
import java.io.FileNotFoundException;

import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;
import static utils.Tags.POST;
import static com.example.sandbox.util.constans.TestData.HYDRAIMAGE;

@Listeners(TestListener.class)
public class PostUploadImageTest extends Common {
    private static final int PET_ID = 100;
    private static final File image = new File(HYDRAIMAGE);
    private static final File invalidImage = new File(HYDRAIMAGE+"UNKNOWN");

    @Test(groups = {POST},description ="Positive test for POST /pet/{petID}/uploadImage")
    public void testPostNewPetById_Success(){
        TestData.testPet.getPetBody().setId(PET_ID);
        //create a dummy pet
        Response response = postUrl(newPet, createJsonBody(TestData.testPet));
        Assertions.assertReturnCode(response, 200);

        //upload an image to an existing dummy pet
        //Response postResponse = postUrlWithImage(uploadImage.replace("{petId}", ""+PET_ID), image);  --> it can be tested without metadata parameter too
        Response postResponse = postUrlWithImage(uploadImage.replace("{petId}", ""+PET_ID),
                image, "METADATA");
        Assertions.assertReturnCode(postResponse, 200);
        Assertions.assertResponseTime(postResponse, 2000);
    }

    //OK
    @Test(groups = {POST},description ="Negative test for POST /pet/{petID}/uploadImage with invalid image path")
    public void testPostNewPetById_Invalid(){
        //try to upload a non-existing image to an existing dummy pet
        Assert.assertThrows(FileNotFoundException.class,
                () -> postUrlWithImage(uploadImage.replace("{petId}", ""+PET_ID),
                        invalidImage, "METADATA"));
    }

}
