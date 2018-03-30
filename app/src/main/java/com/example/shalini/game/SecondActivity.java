package com.example.shalini.game;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.shalini.game.data.ImageService;
import com.example.shalini.game.data.RemoteDataSource;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity{
    private static final String TAG = "SecondActivity";
    private TextView mTextField;
    private RecyclerView mRecyclerView;

    private List<MediaItem> mediaItemList;
    private RemoteDataSource mRemoteDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mTextField = findViewById(R.id.textViewTimer);

        mRecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(layoutManager);

        mRemoteDataSource = RemoteDataSource.getInstance();
        Observable<ImageListModel> observable = mRemoteDataSource.createApiService(ImageService.class).getImageUrlList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe((imageListModel)->{
            Log.e(TAG, "onCreate: "+imageListModel.getImageModelList() );
            MediaItem[] mediaItems = new MediaItem[9];
            for (int i = 0; i < mediaItems.length; i++) {
                mediaItems[i] = imageListModel.getImageModelList().get(i);
            }
            ImageAdapter imageAdapter = new ImageAdapter(Arrays.asList(mediaItems));
            mRecyclerView.setAdapter(imageAdapter);

        });


    }

    private void startCountdown() {
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                mTextField.setText("done!");
            }

        }.start();
    }
ImageLoadListener imageLoadListener = new ImageLoadListener() {
    @Override
    public void setImageLoadingCompleted(boolean loadingCompleted) {
        startCountdown();
    }
};
}
