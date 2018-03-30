package com.example.shalini.game;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shalini Prajesh on 28/3/18.
 */

public class ImageModel {
    @SerializedName("m")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }
}
