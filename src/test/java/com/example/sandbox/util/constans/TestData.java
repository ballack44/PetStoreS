package com.example.sandbox.util.constans;

import com.example.sandbox.util.body.pet.PostCreatePet;
import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.PetBody;

import static com.example.sandbox.util.Tools.generateRandomNumber;

public class TestData {

    //TODO - the following link does not exist, so i downloaded an other to /src folder
    //public static final String HYDRAIMAGE = "https://gods-and-demons.fandom.com/wiki/Lernaean_Hydra?file=Venture_the_fog_hydra_by_darkcloud013_dbpdkjn-fullview.jpg";
    public static final String HYDRAIMAGE = "src/image/Hydra.png";

    public static PostCreatePet testPet = PostCreatePet.builder()
            .petBody(PetBody.builder()
                    .id(generateRandomNumber())
                    .category(Item.builder()
                            .id(1)
                            .name("Hydra")
                            .build())
                    .name("Princess")
                    .photoUrl(HYDRAIMAGE)
                    .tag(Item.builder()
                            .id(2)
                            .name("cute")
                            .build())
                    .status(String.valueOf(PetStatus.AVAILABLE))
                    .build()
            ).build();

    public static final PostCreatePet invalidDummyPet = PostCreatePet.builder()
            .petBody(PetBody.builder()
                    //.id(generateRandomNumber())  --> miss out the ID
                    .category(Item.builder()
                            .id(1)
                            .name("Dummy")
                            .build())
                    //.name("Princess") --> miss out the name
                    .photoUrl(HYDRAIMAGE)
                    .tag(Item.builder()
                            .id(3)
                            .name("nice")
                            .build())
                    //.status("XXX")  --> miss out the status
                    .build()
            ).build();
}
