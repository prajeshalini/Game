package com.example.shalini.game;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.shalini.game.databinding.LayoutRowItemBinding;

import java.util.List;

/**
 * Created by Shalini Prajesh on 28/3/18.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    List<MediaItem> mediaItemList;

    public ImageAdapter(List<MediaItem> mediaItemList) {
        this.mediaItemList = mediaItemList;
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

    class MyViewHolder extends RecyclerView.ViewHolder{
        LayoutRowItemBinding layoutRowItemBinding;

        public MyViewHolder(LayoutRowItemBinding itemView) {
            super(itemView.getRoot());
            layoutRowItemBinding = itemView;
        }

        public void bind(ImageModel imageModel){
            layoutRowItemBinding.setModel(imageModel);
            layoutRowItemBinding.executePendingBindings();
        }
    }
}
