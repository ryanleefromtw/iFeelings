package com.google.firebase.udacity.friendlychat.DetailObjectFragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
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
import com.google.firebase.udacity.friendlychat.CollectionsActivity.comment.CommentsListAdapter;
import com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel.FeelingsInputFragment;
import com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel.MutipleChoicesFeelingsFragment;
import com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel.TrueOrFalseFeelingsFragment;
import com.google.firebase.udacity.friendlychat.DetailsActivity;
import com.google.firebase.udacity.friendlychat.HomeActivity;
import com.google.firebase.udacity.friendlychat.Music.MP3Player;
import com.google.firebase.udacity.friendlychat.Music.MusicControl;
import com.google.firebase.udacity.friendlychat.R;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.wx.wheelview.widget.WheelViewDialog;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObjectDemoFragment extends Fragment implements MusicControl {


    public ObjectDemoFragment() {
        // Required empty public constructor
    }


    View layout;
    public static final String fragmentTag = "ObjectDemo";
    Context context;

    FloatingActionMenu fam;
    com.github.clans.fab.FloatingActionButton getCardFAB;
    FrameLayout whiteBackgroundFrameLayout;
    private SlideUp slideUp;
    private View sliderView, chooseFeelingsPanel;
    boolean slideUpIsVisiable = false;
    boolean chooseDiloagPopuped = false;
    private LinearLayout chooseFeelingsPanelFeeling, chooseFeelingsPanelMutipleChoices;
    private FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    AlertDialog alertDialog;
    TextView textViewNext, textViewfeelingStyle, changeFeelingStyle;
    String nowFeelingsInputType = null;
    boolean isFromChangeButton = false;
    ScrollView scrollView;

    String imageUrl;
    String audioUrl;
    String artName;
    String artDesc;

    public ImageView artImageView;
    public TextView artNameTextView;
    public TextView artDescTextView;

    SeekBar musicSeekBar;
    ImageView playButton;

    SimpleExoPlayer rowPlayer;
    MP3Player mp3Player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getArguments().getString("imageUrl");
        audioUrl = getArguments().getString("audioUrl");
        artName = getArguments().getString("artName");
        artDesc = getArguments().getString("artDesc");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_object_demo, container, false);
        fam = (FloatingActionMenu) layout.findViewById(R.id.fam);
        fam.getMenuIconView().setImageResource(R.drawable.light_bulb);
        getCardFAB = (FloatingActionButton) layout.findViewById(R.id.getCard);
        getCardFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectInteractFragment objectInteractFragment = new ObjectInteractFragment();
                ((HomeActivity) context).changeFragment("GetFeeling", objectInteractFragment);
                ((HomeActivity) context).titleTextView.setText("互動");
                ((HomeActivity) context).titleBackimageView.setVisibility(View.VISIBLE);
            }
        });
        scrollView = (ScrollView) layout.findViewById(R.id.scrollView);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.d("onScroll", scrollY + "");
                    if (scrollY < 10) {
                        fam.setVisibility(View.VISIBLE);
                    } else {
                        fam.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        createCustomAnimation();
        whiteBackgroundFrameLayout = (FrameLayout) layout.findViewById(R.id.background);
        whiteBackgroundFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("whiteBackgroundFrame", "onclick");
                fam.close(false);
                whiteBackgroundFrameLayout.setVisibility(View.GONE);
            }
        });

        fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fam.isOpened()) {
                    slideUp.show();
                } else {
                    fam.open(true);
                    whiteBackgroundFrameLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        artImageView = (ImageView) layout.findViewById(R.id.artImageView);
        Glide.with(this).load(imageUrl).centerCrop().into(artImageView);

        artNameTextView = (TextView) layout.findViewById(R.id.artNameTextView);
        artNameTextView.setText(artName);
        artDescTextView = (TextView) layout.findViewById(R.id.artIntroTextView);
        artDescTextView.setText(artDesc);

        musicSeekBar = (SeekBar) layout.findViewById(R.id.musicSeekBar);
        musicSeekBar.setProgress(0);

        playButton = (ImageView) layout.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp3Player.isPlaying()) {
                    playButton.setImageResource(R.drawable.play_button);
                    mp3Player.pause();
                } else {
                    playButton.setImageResource(R.drawable.ic_pause_circle_outline);
                    mp3Player.start();
                }
            }
        });

        rowPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), new DefaultTrackSelector(), new DefaultLoadControl());

        MediaSource audioSource = makeMediaSource(audioUrl, "ua");
        mp3Player = new MP3Player(rowPlayer, musicSeekBar, this);
        mp3Player.setMP3resource(audioSource);


        fragmentManager = getActivity().getSupportFragmentManager();
        Log.d("getFragmentManager", fragmentManager.toString());
        textViewNext = (TextView) layout.findViewById(R.id.textViewNext);
        textViewfeelingStyle = (TextView) layout.findViewById(R.id.textViewfeelingStyle);
        sliderView = layout.findViewById(R.id.slideView);
        chooseFeelingsPanel = layout.findViewById(R.id.choose_feelings_type_panel);
        changeFeelingStyle = (TextView) layout.findViewById(R.id.textViewfeelingStyle);
        changeFeelingStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFeelingsPanel.setVisibility(View.VISIBLE);
                chooseDiloagPopuped = true;
                isFromChangeButton = true;
            }
        });
        slideUp = new SlideUpBuilder(sliderView)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent <= 20 && percent >= 10 && slideUpIsVisiable) {
                            if (getActivity().getCurrentFocus() != null) {
                                InputMethodManager inputManager = (InputMethodManager)
                                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                            slideUpIsVisiable = false;
                        } else if (percent == 0) {
                            ((HomeActivity) context).titleTextView.setText("創作");
                            ((HomeActivity) context).titleBackimageView.setVisibility(View.VISIBLE);
                            if (nowFeelingsInputType == null) {
                                chooseFeelingsPanel.setVisibility(View.VISIBLE);
                                chooseDiloagPopuped = true;
                                chooseFeelingsPanelMutipleChoices = (LinearLayout) layout.findViewById(R.id.choose_feelings_mutiple_choices);
                                chooseFeelingsPanelMutipleChoices.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        if (isFromChangeButton) {//not first time
                                            showAlertDialog(0);
                                            isFromChangeButton = false;
                                        } else {
                                            changeToMultipleChoiceFragment();
                                        }
                                    }
                                });
                                chooseFeelingsPanelFeeling = (LinearLayout) layout.findViewById(R.id.choose_feelings_feelings);
                                chooseFeelingsPanelFeeling.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (isFromChangeButton) {
                                            showAlertDialog(1);
                                            isFromChangeButton = false;
                                        } else {
                                            changeToFeelingsFragment();
                                        }
                                    }
                                });
                            }
                            slideUpIsVisiable = true;
                        } else if (percent == 100) {
                            ((HomeActivity) context).titleTextView.setText("展品");
                            if (chooseDiloagPopuped) {
                                chooseFeelingsPanel.setVisibility(View.INVISIBLE);
                                chooseDiloagPopuped = false;
                            }
                        } else if (percent <= 20 && percent >= 10) {
                            if (chooseDiloagPopuped) {
                                chooseFeelingsPanel.setVisibility(View.INVISIBLE);
                                chooseDiloagPopuped = false;
                            }
                        }
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
        return layout;
    }

    public MediaSource makeMediaSource(String url, String userAgent) {
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory(userAgent),
                new DefaultExtractorsFactory(), null, null);
        return mediaSource;
    }

    @Override
    public void onfinish() {

    }

    public void changeToMultipleChoiceFragment() {
        MutipleChoicesFeelingsFragment choicesFragment = new MutipleChoicesFeelingsFragment();
        choicesFragment.setNext(textViewNext);
        fragmentTransaction.replace(R.id.writing_panel_container, choicesFragment);
        fragmentTransaction.commit();
        textViewfeelingStyle.setText("選擇題模式");
        chooseFeelingsPanel.setVisibility(View.INVISIBLE);
        nowFeelingsInputType = "MultilpleChoice";
    }

    public void changeToFeelingsFragment() {
        FeelingsInputFragment feelingsInputFragment = new FeelingsInputFragment();
        feelingsInputFragment.setNext(textViewNext);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.writing_panel_container, feelingsInputFragment);
        fragmentTransaction.commit();
        textViewfeelingStyle.setText("感想模式");
        chooseFeelingsPanel.setVisibility(View.INVISIBLE);
        nowFeelingsInputType = "Feeling";
    }


    private void showAlertDialog(final int feelingInputFragment) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("確定嗎?")
                .setMessage("更改互動模式,現在所寫的將不會被保存")
                .setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        if (chooseDiloagPopuped)
                            chooseFeelingsPanel.setVisibility(View.INVISIBLE);
                    }
                }).setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                if (feelingInputFragment == 0) {
                    changeToMultipleChoiceFragment();
                } else {
                    changeToFeelingsFragment();
                }
                if (chooseDiloagPopuped)
                    chooseFeelingsPanel.setVisibility(View.INVISIBLE);
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == true)
            ((HomeActivity) context).tabStateManager.ObjectState = fragmentTag;
    }

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
                    fam.getMenuIconView().setImageResource(R.drawable.light_bulb);
                } else {
                    fam.getMenuIconView().setImageResource(R.drawable.edit);
                }
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));


        fam.setIconToggleAnimatorSet(set);
    }
}
