package com.zwt.zwttransmit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.zwt.zwttransmit.fragment.SideBar;
import com.zwt.zwttransmit.modle.Music;
import com.zwt.zwttransmit.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Map;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{

    private Context context;
    private Map<String, ArrayList<Music>> musicGroupMap;

    public MusicAdapter(Map<String, ArrayList<Music>> musicGroupMap) {
        this.musicGroupMap = musicGroupMap;
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.yinyue_group_item, parent, false);
        return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
        ArrayList<Music> list = musicGroupMap.get(SideBar.letters[position]);
        MusicAdapter.ChildAdapter childAdapter = (MusicAdapter.ChildAdapter) holder.recyclerView.getAdapter();

        if (childAdapter == null) {
            GridLayoutManager manager = new GridLayoutManager(context, 1);
            holder.recyclerView.setLayoutManager(manager);
            holder.recyclerView.setAdapter(new MusicAdapter.ChildAdapter(list));
        }else {
            childAdapter.setData(list);
            childAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return musicGroupMap.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            recyclerView = (RecyclerView) view.findViewById(R.id.yinyue_group_item_recyclerView);
        }
    }

    /****************ChildAdapter*************************/
    public class ChildAdapter extends RecyclerView.Adapter<MusicAdapter.ChildAdapter.ChildViewHolder>{

        private ArrayList<Music> musicList;

        public ChildAdapter(ArrayList<Music> musicList) {
            this.musicList = musicList;
        }

        public void setData(ArrayList<Music> musicList){
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (context == null){
                context = parent.getContext();
            }
            View view = LayoutInflater.from(context).inflate(R.layout.yinyue_child_item, parent, false);
            return new ChildViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
            Music music = musicList.get(position);
            holder.textView.setText(music.getName()+"."+music.getContentType());
            Glide.with(context).load(music.getAlbumArt()).into(holder.imageView);
            holder.textViewInformation.setText(music.getSize()+ "  |  "+ TimeUtils.getDateMinute(music.getTime()));
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        class ChildViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            TextView textView;
            ImageView imageView;
            TextView textViewInformation;
            public ChildViewHolder(View view){
                super(view);
                cardView = (CardView)view;
                textView = (TextView) view.findViewById(R.id.yinyue_child_item_textView);
                imageView = (ImageView) view.findViewById(R.id.yinyue_child_item_imageView);
                textViewInformation = (TextView) view.findViewById(R.id.yinyue_child_item_textView_information);
            }
        }
    }

}
