package com.zwt.zwttransmit.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    }

    @Override
    public int getItemCount() {
        return photoMap.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /*********************************ChildAdapter***************************/
    public class ChildAdapter extends RecyclerView.Adapter<PhotoAdapter.ChildAdapter.ChildViewHolder>{


    }

}
