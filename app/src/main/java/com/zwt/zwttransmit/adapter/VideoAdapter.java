package com.zwt.zwttransmit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zwt.zwttransmit.R;
import com.zwt.zwttransmit.modle.Photo;
import com.zwt.zwttransmit.modle.Video;
import com.zwt.zwttransmit.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Map;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    private Context context;
    Map<String, ArrayList<Video>> videoMap;
    ArrayList<String> videoMapKey;

    public VideoAdapter(Map<String, ArrayList<Video>> videoMap){
        this.videoMap = videoMap;
        videoMapKey = new ArrayList<String>(videoMap.keySet());
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.shipin_group_item, parent, false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        ArrayList<Video> list = videoMap.get(videoMapKey.get(position));
        VideoAdapter.ChildAdapter childAdapter = (VideoAdapter.ChildAdapter)holder.recyclerView.getAdapter();
        if (childAdapter == null){
            GridLayoutManager manager = new GridLayoutManager(context, 1);

            holder.recyclerView.setLayoutManager(manager);

            holder.recyclerView.setAdapter(new VideoAdapter.ChildAdapter(list));
        }else {
            childAdapter.setData(list);
            childAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return videoMap.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            recyclerView = (RecyclerView)itemView.findViewById(R.id.shipin_group_item_recyclerView);
        }
    }
    /****************ChildAdapter*************************/

    public class ChildAdapter extends RecyclerView.Adapter<VideoAdapter.ChildAdapter.ChildViewHolder>{

        private ArrayList<Video> videoArrayList;

        public ChildAdapter(ArrayList<Video> videoArrayList) {
            this.videoArrayList = videoArrayList;
        }

        public void setData(ArrayList<Video> videoArrayList){
            this.videoArrayList = videoArrayList;
        }

        @NonNull
        @Override
        public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (context == null){
                context = parent.getContext();
            }
            View view = LayoutInflater.from(context).inflate(R.layout.shipin_child_item, parent, false);
            return new ChildViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
            Video video = videoArrayList.get(position);
            holder.textView.setText(video.getName());

            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(video.getPath());

            Glide.with(context).load(media.getFrameAtTime()).into(holder.imageView);
//            Glide.with(context).load(video.getThumbnailData()).into(holder.imageView);
            holder.textViewInformation.setText(video.getSize()+"  |  "+ TimeUtils.getDateMinute(video.getTime()));
        }

        @Override
        public int getItemCount() {
            return videoArrayList.size();
        }

        class ChildViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            TextView textView;
            ImageView imageView;
            TextView textViewInformation;

            public ChildViewHolder(@NonNull View view) {
                super(view);
                cardView = (CardView)view;
                textView = (TextView) view.findViewById(R.id.shipin_child_item_textView);
                imageView = (ImageView) view.findViewById(R.id.shipin_child_item_imageView);
                textViewInformation = (TextView) view.findViewById(R.id.shipin_child_item_textView_information);
            }
        }
    }
}
