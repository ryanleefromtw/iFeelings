package com.google.firebase.udacity.friendlychat.Music;

import android.net.Uri;

import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

/**
 * Created by X220 on 2017/7/14.
 */

public class MP3Data {
    String userAgent;
    String url;
    MediaSource mediaSource;
    int lastPosition = 0;


    public MP3Data(String url, String userAgent) {
        this.url = url;
        this.userAgent = userAgent;
    }

    public MediaSource makeMediaSource() {
        Uri uri = Uri.parse(url);
        mediaSource = new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory(userAgent),
                new DefaultExtractorsFactory(), null, null);
        return mediaSource;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }
}
