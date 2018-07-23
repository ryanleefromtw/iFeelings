package com.google.firebase.udacity.friendlychat.Music;

import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.firebase.udacity.friendlychat.R;

/**
 * Created by X220 on 2017/7/14.
 */

public class MP3Player implements MediaController.MediaPlayerControl {

    public final ExoPlayer exoPlayer;
    public SeekBar seekBar;
    final MusicControl musicControl;

    public MP3Player(final ExoPlayer exoPlayer, final SeekBar seekBar, final MusicControl musicControl) {
        this.exoPlayer = exoPlayer;
        this.seekBar = seekBar;
        this.musicControl = musicControl;
        exoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.d("loading : ", isLoading + "");
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_ENDED) {
                    exoPlayer.setPlayWhenReady(false);
                    exoPlayer.seekTo(0);
                    seekBar.setVisibility(View.GONE);
                    musicControl.onfinish();
                }
                if (playbackState == ExoPlayer.STATE_BUFFERING) {
                    Log.d("playStateChanged : ", "buffering" + exoPlayer.getBufferedPercentage());
                }
                if (playbackState == exoPlayer.STATE_READY) {
                    Log.d("playStateChanged : ", "ready " + exoPlayer.getBufferedPercentage());
                }
                if (playWhenReady) {
                    primarySeekBarProgressUpdater();
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (getCurrentPosition() >= 0) {
                    Log.d("SeekBar position", seekBar.getProgress() + "");
                    int playposition = (getDuration() / 100) * seekBar.getProgress();
                    seekTo(playposition);
                }
            }
        });
    }

    public void setMP3resource(MediaSource mediaSource) {
        exoPlayer.prepare(mediaSource);
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBufferPercentage() {
        return exoPlayer.getBufferedPercentage();
    }

    @Override
    public int getCurrentPosition() {
        return exoPlayer.getDuration() == com.google.android.exoplayer2.C.TIME_UNSET ? 0
                : (int) exoPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return exoPlayer.getDuration() == com.google.android.exoplayer2.C.TIME_UNSET ? 0
                : (int) exoPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return exoPlayer.getPlayWhenReady();
    }


    @Override
    public void start() {
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void seekTo(int timeMillis) {

        long seekPosition = exoPlayer.getDuration() == com.google.android.exoplayer2.C.TIME_UNSET ? 0
                : Math.min(Math.max(0, timeMillis), getDuration());
        if (isPlaying()) {
            pause();
            exoPlayer.seekTo(seekPosition);
            start();
        } else {
            exoPlayer.seekTo(seekPosition);
        }
    }

    Handler handler = new Handler();

    private void primarySeekBarProgressUpdater() {
        seekBar.setProgress((int) (((float) getCurrentPosition() / getDuration()) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                    Log.d("isPlaying", "true");
                }
            };
            handler.postDelayed(notification, 1000);//dealy for 1 second
        }
    }
}
