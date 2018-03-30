package com.example.shalini.game;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shalini.game.data.ImageService;
import com.example.shalini.game.data.RemoteDataSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity implements ImageLoadListener{
    private static final String TAG = "SecondActivity";
    private TextView mTextField;
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private Button mbuttonReset;

    private List<MediaItem> mediaItemList;
    private RemoteDataSource mRemoteDataSource;
    private ImageAdapter imageAdapter;
    private int tryCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mTextField = findViewById(R.id.textViewTimer);
        mImageView = findViewById(R.id.imageViewRandom);
        mbuttonReset = findViewById(R.id.buttonReset);

        mRecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(layoutManager);

       resetImages();
        mbuttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetImages();
            }
        });

    }

    private void resetImages() {
        tryCounter = 0;
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
            imageAdapter = new ImageAdapter(Arrays.asList(mediaItems),this);
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
                mImageView.setVisibility(View.VISIBLE);
                String randomImageUrl = mediaItemList.get(new Random().nextInt(mediaItemList.size())).getImageModel().getImageUrl();
                imageAdapter.flipAllViews(randomImageUrl);
                Glide.with(SecondActivity.this)
                        .load(randomImageUrl)
                        .centerCrop()
                        .into(mImageView);
            }

        }.start();
    }

    @Override
    public void isImageSelectedCorrect(boolean isImageCorrect) {
        tryCounter++;
        if (tryCounter <= 2){
            if (isImageCorrect){
                Toast.makeText(this,"YOU WON",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Try Again",Toast.LENGTH_SHORT).show();
            }
        }
        if (tryCounter == 2){
            mbuttonReset.setVisibility(View.VISIBLE);
        }

    }
}
