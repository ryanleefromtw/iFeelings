package com.google.firebase.udacity.friendlychat.cards;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.firebase.udacity.friendlychat.Music.MP3Player;
import com.google.firebase.udacity.friendlychat.Music.MusicControl;
import com.google.firebase.udacity.friendlychat.Object;
import com.google.firebase.udacity.friendlychat.ObjectInfo;
import com.google.firebase.udacity.friendlychat.R;

import java.net.URI;
import java.util.HashMap;


public class SliderAdapter extends RecyclerView.Adapter<SliderCard> {

    private final int count;
    HashMap<Integer, ObjectInfo> ObjectInfo;
    private final View.OnClickListener listener;
    Object object;

    public SliderAdapter(HashMap<Integer, ObjectInfo> ObjectInfo, int count, View.OnClickListener listener, Object object) {
        this.ObjectInfo = ObjectInfo;
        this.count = count;
        this.listener = listener;
        this.object = object;
    }


    @Override
    public SliderCard onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_slider_card, parent, false);

        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }

        return new SliderCard(view);
    }


    @Override
    public void onBindViewHolder(SliderCard holder, int position) {
        Log.d("onBindViewHolder",object.toString()+" "+ObjectInfo.get(position).photo.toString()+" "+holder.imageView);
        Glide.with(object)
                .load(ObjectInfo.get(position).photo.toString())
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public void onViewRecycled(SliderCard holder) {
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return count;
    }

}
