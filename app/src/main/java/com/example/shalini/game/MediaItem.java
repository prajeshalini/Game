package com.example.shalini.game;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shalini Prajesh on 28/3/18.
 */

public class MediaItem {
    @SerializedName("media")
    private ImageModel imageModel;

    public ImageModel getImageModel() {
        return imageModel;
    }
}
