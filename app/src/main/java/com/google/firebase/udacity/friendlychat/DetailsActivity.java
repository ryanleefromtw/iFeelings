package com.google.firebase.udacity.friendlychat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel.FeelingsInputFragment;
import com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel.MutipleChoicesFeelingsFragment;
import com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel.TrueOrFalseFeelingsFragment;
import com.google.firebase.udacity.friendlychat.Music.MP3Player;
import com.google.firebase.udacity.friendlychat.Music.MusicControl;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.wx.wheelview.widget.WheelViewDialog;

import java.lang.*;
import java.lang.Object;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, NestedScrollView.OnScrollChangeListener, MusicControl, SlideUp.Listener.Events {

    static final String BUNDLE_IMAGE_ID = "BUNDLE_IMAGE_ID";
    NestedScrollView nestedScrollView;
    FloatingActionMenu fam;
    FloatingActionButton playFAB;
    com.github.clans.fab.FloatingActionButton getCardFAB;
    CollapsingToolbarLayout collapsingToolbarLayout;
    SeekBar seekBar;
    boolean isPlaying = false;
    boolean artIntroIsExpand = false;
    boolean authorIntroIsExpand = false;
    ImageView artExpandButton, authorExpandButton, detailImageView;
    TextView artIntrotextView, authorIntrotextView, artTitle, textViewNext, textViewFeelingStyle;
    CardView cardViewArtIntro, cardViewAuthorIntro;
    SimpleExoPlayer rowPlayer;
    MP3Player mp3Player;

    String artName, artDesc, authorName, authorDesc;
    AppBarLayout appBarLayout;
    FrameLayout whiteBackgroundFrameLayout;
    private SlideUp slideUp;
    private View sliderView;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    boolean slideUpIsVisiable = false;
    boolean chooseDiloagPopuped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(this);
        fam = (FloatingActionMenu) findViewById(R.id.fam);
        createCustomAnimation();
        playFAB = (FloatingActionButton) findViewById(R.id.playfab);
        playFAB.setOnClickListener(this);
        getCardFAB = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.getCard);
        getCardFAB.setOnClickListener(this);
        playFAB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e68284")));
        seekBar = (SeekBar) findViewById(R.id.musicSeekBar);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        artIntrotextView = (TextView) findViewById(R.id.art_intro_textView);
        artExpandButton = (ImageView) findViewById(R.id.expanded_button4artIntro);
        artExpandButton.setOnClickListener(this);

        authorIntrotextView = (TextView) findViewById(R.id.author_intro_textView);
        authorExpandButton = (ImageView) findViewById(R.id.expanded_button4authorIntro);
        authorExpandButton.setOnClickListener(this);

        artTitle = (TextView) findViewById(R.id.detailArtName);

        cardViewArtIntro = (CardView) findViewById(R.id.art_intro);
        cardViewArtIntro.setOnClickListener(this);
        cardViewAuthorIntro = (CardView) findViewById(R.id.author_intro);
        cardViewAuthorIntro.setOnClickListener(this);
        detailImageView = (ImageView) findViewById(R.id.detailArtImageView);

        String photoUrl = this.getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(photoUrl).centerCrop().into(detailImageView);

        String audioUrl = this.getIntent().getStringExtra("audioUrl");
        rowPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), new DefaultTrackSelector(), new DefaultLoadControl());

        MediaSource audioSource = makeMediaSource(audioUrl, "ua");
        mp3Player = new MP3Player(rowPlayer, seekBar, this);
        mp3Player.setMP3resource(audioSource);

        artName = this.getIntent().getStringExtra("artName");
        artDesc = this.getIntent().getStringExtra("artDesc");
        authorName = this.getIntent().getStringExtra("authorName");
        authorDesc = this.getIntent().getStringExtra("authorDesc");
        artTitle.setText(artName);
        artIntrotextView.setText(artDesc);
        authorIntrotextView.setText(authorName + "\n" + authorDesc);

        whiteBackgroundFrameLayout = (FrameLayout) findViewById(R.id.background);
        whiteBackgroundFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fam.close(false);
                whiteBackgroundFrameLayout.setVisibility(View.GONE);
                playFAB.setVisibility(View.VISIBLE);
                playFAB.show();
            }
        });

        fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fam.isOpened()) {
                    slideUp.show();
                    whiteBackgroundFrameLayout.setClickable(false);
                } else {
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                    AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                    behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                        @Override
                        public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                            return false;
                        }
                    });
                    params.setBehavior(behavior);

                    playFAB.setVisibility(View.GONE);
                    fam.open(true);
                    whiteBackgroundFrameLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        sliderView = findViewById(R.id.slideView);
        slideUp = new SlideUpBuilder(sliderView)
                .withListeners(this)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
//        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        textViewNext = (TextView) findViewById(R.id.textViewNext);
        textViewFeelingStyle = (TextView) findViewById(R.id.textViewfeelingStyle);
        textViewFeelingStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                ChooseDialog.show();
            }
        });

        ChooseDialog = new WheelViewDialog(this);
        ChooseDialog.setTitle("互動模式").setItems(new String[]{"感想", "選擇", "是非", "共筆"})
                .setButtonText("確定").setDialogStyle(Color
                .parseColor("#6699ff"));

    }

    AlertDialog alertDialog;

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleX", 1f, 0.1f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleY", 1f, 0.1f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleX", 0.1f, 1f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleY", 0.1f, 1f);
        scaleOutX.setDuration(20);
        scaleOutY.setDuration(20);

        scaleInX.setDuration(80);
        scaleInY.setDuration(80);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (fam.isOpened()) {
                    fam.getMenuIconView().setImageResource(R.drawable.ic_add);
                } else {
                    fam.getMenuIconView().setImageResource(R.drawable.ic_create);
                }
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));


        fam.setIconToggleAnimatorSet(set);
    }


    @Override
    public void onBackPressed() {
        artTitle.setVisibility(View.GONE);
        super.onBackPressed();
    }

    public MediaSource makeMediaSource(String url, String userAgent) {
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory(userAgent),
                new DefaultExtractorsFactory(), null, null);
        return mediaSource;
    }


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
                               int oldScrollY) {
        Log.d("scrollX : ", nestedScrollView.getScrollY() + " old scroll : " + oldScrollY);
        if (scrollY > 5) {
            fam.close(false);
            fam.setVisibility(View.GONE);
        } else {
            Log.d("scrollX : visible", nestedScrollView.getScrollY() + "");
            fam.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.expanded_button4artIntro || v.getId() == R.id.art_intro) {
            if (artIntroIsExpand) {
                artExpandButton.setImageResource(R.drawable.ic_keyboard_arrow_down);
                artIntrotextView.setVisibility(View.GONE);
                artIntroIsExpand = false;
            } else {
                artExpandButton.setImageResource(R.drawable.ic_keyboard_arrow_up);
                artIntrotextView.setVisibility(View.VISIBLE);
                artIntroIsExpand = true;
            }
            if (authorIntroIsExpand) {
                authorExpandButton.setImageResource(R.drawable.ic_keyboard_arrow_down);
                authorIntrotextView.setVisibility(View.GONE);
                authorIntroIsExpand = false;
            }
            return;
        } else if (v.getId() == R.id.expanded_button4authorIntro || v.getId() == R.id.author_intro) {
            if (authorIntroIsExpand) {
                authorExpandButton.setImageResource(R.drawable.ic_keyboard_arrow_down);
                authorIntrotextView.setVisibility(View.GONE);
                authorIntroIsExpand = false;
            } else {
                authorExpandButton.setImageResource(R.drawable.ic_keyboard_arrow_up);
                authorIntrotextView.setVisibility(View.VISIBLE);
                authorIntroIsExpand = true;
            }

            if (artIntroIsExpand) {
                artExpandButton.setImageResource(R.drawable.ic_keyboard_arrow_down);
                artIntrotextView.setVisibility(View.GONE);
                artIntroIsExpand = false;
            }
            return;
        } else if (v.getId() == R.id.playfab) {
            if (mp3Player.isPlaying()) {
                artTitle.setVisibility(View.VISIBLE);
                playFAB.setImageResource(R.drawable.ic_play_arrow);
                seekBar.setVisibility(View.GONE);
                mp3Player.pause();
            } else {
                artTitle.setVisibility(View.GONE);
                playFAB.setImageResource(R.drawable.ic_pause);
                seekBar.setVisibility(View.VISIBLE);
                mp3Player.start();
            }
            return;
        } else if (v.getId() == R.id.getCard) {
            startActivity(new Intent(DetailsActivity.this, GetFeelingsActivity.class));
        }
    }

    @Override
    public void onfinish() {
        playFAB.setImageResource(R.drawable.ic_play_arrow);
        artTitle.setVisibility(View.VISIBLE);
    }

    private WheelViewDialog ChooseDialog;

    @Override
    public void onSlide(float percent) {
        if (percent <= 20 && percent >= 10 && slideUpIsVisiable) {
            if (getCurrentFocus() != null) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            slideUpIsVisiable = false;
        } else if (percent == 0) {
            if (!chooseDiloagPopuped && !slideUpIsVisiable) {
                ChooseDialog.show();
                ChooseDialog.getmDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        slideUp.hide();
                    }
                });
                ChooseDialog.setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
                    @Override
                    public void onItemClick(int position, Object s) {
                        final int pos = position;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        if (fragmentManager.getFragments().size() > 1) {//not first time
                            final AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                            builder.setTitle("確定嗎?")
                                    .setMessage("更改互動模式,現在所寫的將不會被保存")
                                    .setPositiveButton("否", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    }).setNegativeButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (pos) {
                                        case 0:
                                            FeelingsInputFragment feelingsInputFragment = new FeelingsInputFragment();
                                            feelingsInputFragment.setNext(textViewNext);
                                            fragmentTransaction.replace(R.id.writing_panel_container, feelingsInputFragment);
                                            fragmentTransaction.commit();
                                            break;
                                        case 1:
                                            MutipleChoicesFeelingsFragment choicesFragment = new MutipleChoicesFeelingsFragment();
                                            choicesFragment.setNext(textViewNext);
                                            fragmentTransaction.replace(R.id.writing_panel_container, choicesFragment);
                                            fragmentTransaction.commit();
                                            break;
                                        case 2:
                                            Log.d("jifjsdifadjif1", "in");
                                            TrueOrFalseFeelingsFragment trueOrFalseFeelingsFragment = new TrueOrFalseFeelingsFragment();
                                            trueOrFalseFeelingsFragment.setNext(textViewNext);
                                            fragmentTransaction.replace(R.id.writing_panel_container, trueOrFalseFeelingsFragment);
                                            fragmentTransaction.commit();
                                            break;
                                    }

                                }
                            });
                            alertDialog = builder.create();
                            alertDialog.show();
                        } else if (fragmentManager.getFragments().size() == 1) {//first time

                            switch (pos) {
                                case 0:
                                    FeelingsInputFragment feelingsInputFragment = new FeelingsInputFragment();
                                    fragmentTransaction.replace(R.id.writing_panel_container, feelingsInputFragment);
                                    fragmentTransaction.commit();
                                    break;
                                case 1:
                                    MutipleChoicesFeelingsFragment choicesFragment = new MutipleChoicesFeelingsFragment();
                                    choicesFragment.setNext(textViewNext);
                                    fragmentTransaction.replace(R.id.writing_panel_container, choicesFragment);
                                    fragmentTransaction.commit();
                                    break;
                                case 2:
                                    Log.d("jifjsdifadjif2", "in");
                                    TrueOrFalseFeelingsFragment trueOrFalseFeelingsFragment = new TrueOrFalseFeelingsFragment();
                                    trueOrFalseFeelingsFragment.setNext(textViewNext);
                                    fragmentTransaction.replace(R.id.writing_panel_container, trueOrFalseFeelingsFragment);
                                    fragmentTransaction.commit();
                                    break;
                            }
                        }
                        chooseDiloagPopuped = true;
                    }
                });
            }
            slideUpIsVisiable = true;
        }
    }


    @Override
    public void onVisibilityChanged(int visibility) {
        if (visibility == View.GONE) {
            fam.setVisibility(View.VISIBLE);
            whiteBackgroundFrameLayout.setClickable(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (slideUpIsVisiable) {
                slideUp.hide();
            } else if (fam.isOpened() && !slideUp.isVisible()) {
                fam.close(false);
                whiteBackgroundFrameLayout.setVisibility(View.GONE);
                playFAB.setVisibility(View.VISIBLE);
            } else if (ChooseDialog.getmDialog().isShowing()) {
                ChooseDialog.dismiss();
            } else {
                onBackPressed();
            }
            return true;
        }
        return false;
    }
}