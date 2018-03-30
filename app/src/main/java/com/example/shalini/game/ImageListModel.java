package com.example.shalini.game;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shalini Prajesh on 28/3/18.
 */

public class ImageListModel {
    @SerializedName("items")
    private List<MediaItem> imageModelList;

    public List<MediaItem> getImageModelList() {
        return imageModelList;
    }
}
