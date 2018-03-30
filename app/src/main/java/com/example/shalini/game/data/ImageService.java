package com.example.shalini.game.data;

import com.example.shalini.game.ImageListModel;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by Shalini Prajesh on 28/3/18.
 */

public interface ImageService {
    @GET("photos_public.gne?format=json&nojsoncallback=1")
    Observable<ImageListModel> getImageUrlList();
}
