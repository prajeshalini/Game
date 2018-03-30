package com.example.shalini.game;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.shalini.game.databinding.LayoutRowItemBinding;

import java.util.List;

/**
 * Created by Shalini Prajesh on 28/3/18.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    List<MediaItem> mediaItemList;
    boolean showImages = true;
    private String mRandomUrl;
    ImageLoadListener mImageLoadListener;

    public ImageAdapter(List<MediaItem> mediaItemList, ImageLoadListener imageLoadListener) {
        this.mediaItemList = mediaItemList;
        mImageLoadListener = imageLoadListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutRowItemBinding layoutRowItemBinding = LayoutRowItemBinding.inflate(layoutInflater);
        return new MyViewHolder(layoutRowItemBinding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MediaItem mediaItem = mediaItemList.get(position);
        holder.bind(mediaItem.getImageModel());

        if (showImages){
            holder.viewFlipper.setDisplayedChild(0);
        }else{
            holder.viewFlipper.setDisplayedChild(1);
        }
    }

    @Override
    public int getItemCount() {
        return mediaItemList.size();
    }

    public void replaceData(List<MediaItem> mediaList) {
        setList(mediaList);
        notifyDataSetChanged();
    }

    private void setList(List<MediaItem> mediaList) {
        mediaItemList = mediaList;
    }

    public void flipAllViews(String url) {
        showImages = false;
        notifyDataSetChanged();
        mRandomUrl = url;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final ViewFlipper viewFlipper;
        LayoutRowItemBinding layoutRowItemBinding;

        public MyViewHolder(LayoutRowItemBinding itemView) {
            super(itemView.getRoot());
            layoutRowItemBinding = itemView;
            viewFlipper = itemView.getRoot().findViewById(R.id.my_view_flipper);

           // flipViewFlipper(viewFlipper);
            //now you set your onclick and pass it the current viewflipper to control the displayed child
            viewFlipper.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View click) {

                    flipViewFlipper(viewFlipper);
                    if (mRandomUrl.equals(mediaItemList.get(getAdapterPosition()).getImageModel().getImageUrl())){
                        mImageLoadListener.isImageSelectedCorrect(true);
                    }else{
                        mImageLoadListener.isImageSelectedCorrect(false);
                    }
                }

            });

        }

        private void flipViewFlipper(ViewFlipper viewFlipper) {
            if(viewFlipper.getDisplayedChild() == 0){
                viewFlipper.setDisplayedChild(1);
            } else{
                viewFlipper.setDisplayedChild(0);
            }
        }

        public void bind(ImageModel imageModel){
            layoutRowItemBinding.setModel(imageModel);
            layoutRowItemBinding.executePendingBindings();
        }
    }
}
