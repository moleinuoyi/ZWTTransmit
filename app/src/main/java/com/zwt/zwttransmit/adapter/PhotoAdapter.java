package com.zwt.zwttransmit.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zwt.zwttransmit.R;
import com.zwt.zwttransmit.modle.Photo;

import java.util.ArrayList;
import java.util.Map;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    Map<String, ArrayList<Photo>> photoMap;
    ArrayList<String> photoMapKey;

    public PhotoAdapter(Map<String, ArrayList<Photo>> photoMap){
        this.photoMap = photoMap;
        photoMapKey = new ArrayList<String>(photoMap.keySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.tupian_group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<Photo> list = photoMap.get(photoMapKey.get(position));
        PhotoAdapter.ChildAdapter childAdapter = (PhotoAdapter.ChildAdapter) holder.recyclerView.getAdapter();
        if (childAdapter == null){
            GridLayoutManager manager = new GridLayoutManager(context, 4);
            holder.recyclerView.setLayoutManager(manager);

            holder.recyclerView.setAdapter(new PhotoAdapter.ChildAdapter(list));
        }else {

            childAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return photoMap.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            recyclerView = (RecyclerView)itemView.findViewById(R.id.tupian_group_item_recyclerView);
        }
    }

    /*********************************ChildAdapter***************************/
    public class ChildAdapter extends RecyclerView.Adapter<PhotoAdapter.ChildAdapter.ChildViewHolder>{
        public ArrayList<Photo> photoList;

        public ChildAdapter(ArrayList<Photo> photoList){
            this.photoList = photoList;
        }

        public void setData(ArrayList<Photo> photoList){
            this.photoList = photoList;
        }

        @NonNull
        @Override
        public ChildAdapter.ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (context == null){
                context = parent.getContext();
            }
            View view = LayoutInflater.from(context).inflate(R.layout.tupian_child_item, parent, false);

            return new ChildAdapter.ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChildAdapter.ChildViewHolder holder, int position) {
            Photo image = photoList.get(position);
            Glide.with(context).load(image.getFilePath()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return photoList.size();
        }

        class ChildViewHolder extends RecyclerView.ViewHolder{
            CardView cardView;
            ImageView imageView;
            public ChildViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = (CardView)itemView;
                imageView = (ImageView)itemView.findViewById(R.id.tipian_child_item_imageView);
            }
        }
    }

}
