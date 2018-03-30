package com.example.shalini.game;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shalini.game.data.ImageService;
import com.example.shalini.game.data.RemoteDataSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity{
    private static final String TAG = "SecondActivity";
    private TextView mTextField;
    private RecyclerView mRecyclerView;
    private ImageView mImageView;

    private List<MediaItem> mediaItemList;
    private RemoteDataSource mRemoteDataSource;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mTextField = findViewById(R.id.textViewTimer);
        mImageView = findViewById(R.id.imageViewRandom);

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
            mediaItemList = Arrays.asList(mediaItems);
            imageAdapter = new ImageAdapter(Arrays.asList(mediaItems));
            mRecyclerView.setAdapter(imageAdapter);
            startCountdown();
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
                imageAdapter.flipAllViews();
                mImageView.setImageURI(Uri.parse(mediaItemList.get(new Random().nextInt(mediaItemList.size())).getImageModel().getImageUrl()));
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
