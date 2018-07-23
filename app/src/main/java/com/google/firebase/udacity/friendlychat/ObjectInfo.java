package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.google.firebase.udacity.friendlychat.Music.MP3Data;
import com.google.firebase.udacity.friendlychat.cards.SliderAdapter;

/**
 * Created by X220 on 2017/7/16.
 */

public class ObjectInfo {

    public String artName;
    public String artDes;
    public String authorName;
    public String authorIntro;
    public String audio;
    public String photo;
    public String beaconId2;

    public ObjectInfo(String objectName, String objectDesc, String objectPhotoUrl
            , String objectMP3Url, String authorName, String authorDesc) {
        this.artName = objectName;
        this.artDes = objectDesc;
        this.photo = objectPhotoUrl;
        this.audio = objectMP3Url;
        this.authorName = authorName;
        this.authorIntro = authorDesc;
    }

    @Override
    public String toString() {
        return artName + " : " + artDes + "," + artDes + "audio : " + audio + ",";
    }
}
