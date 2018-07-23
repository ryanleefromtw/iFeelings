package com.google.firebase.udacity.friendlychat;

import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.FeelingsFeedback.BaseFeeling;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingMutipleChoices;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingPure;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingTureOrFalse;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.Ichoiceable;
import com.google.firebase.udacity.friendlychat.GetFeelingsCard.CardsDataAdapter;

import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GetFeelingsActivity extends AppCompatActivity {

    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    private com.github.clans.fab.FloatingActionButton mfabNext, mfabWrite;
    private LinearLayout slideUpView;
    private SlideUp slideUp;
    private ImageView sendButton;
    private EditText inputEditText;
    private LinearLayout choiceContainer;
    private LinearLayout replyPanle;
    private LinearLayout titleBar;
    private ImageView mBackButton;


    public int position = 0;
    public boolean keyBoardIsShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        }

        setContentView(R.layout.activity_get_feelings);

        mBackButton = (ImageView)findViewById(R.id.topBackImageViewButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mfabNext = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_next);
        mfabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.setText("");
                selectedAns = -1;
                dragCard();
                if (position < mCardAdapter.getCount() - 1) {
                    position++;
                    setupPopUpPanel(mCardAdapter.getItem(position));
                    Log.d("LogPosition", position + " " + mCardAdapter.getCount());
                }
                if (position == mCardAdapter.getCount() - 2) {
                    setCard();
                }
            }
        });



        mfabWrite = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_edit);
        mfabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!slideUp.isVisible()) {
                    slideUp.show();
                }
            }
        });

        slideUpView = (LinearLayout) findViewById(R.id.feedback_slide_up_view);
        initResponceComponent();
        mCardStack = (CardStack) findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_content);
        mCardAdapter = new CardsDataAdapter(getApplicationContext(),new FrameLayout(getApplicationContext()));

        setCard();

        mCardStack.setAdapter(mCardAdapter);
        mCardStack.setStackMargin(0);
        if (mCardStack.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }


        EditText editText = (EditText) findViewById(R.id.inputEditText);
        editText.setMaxLines(4);
        slideUp = new SlideUpBuilder(slideUpView)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
        slideUp.setAutoSlideDuration(500);
        setupPopUpPanel(mCardAdapter.getItem(0));
    }

    public void setCard() {
//        mCardAdapter.add(new FeelingPure("那些年我們一起追的女孩", "f", getResources().getString(R.string.description1)));
//        mCardAdapter.add(new FeelingTureOrFalse("你覺得朴樹帥嗎?", "tof", 1, "幹超帥"));
//        mCardAdapter.add(new FeelingMutipleChoices("愛我嗎?", "m", "爽", 3, getFeelingMutipleChoicesOptions()));
    }

    public void setupPopUpPanel(BaseFeeling feeling) {
        choiceContainer.removeAllViews();

        String type = feeling.getType();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.writingArea);
        linearLayout.setVisibility(View.VISIBLE);

        if (type.equals("f")) {
            View writingArea = LayoutInflater.from(this).inflate(R.layout.writing_area, null);
            linearLayout.setVisibility(View.GONE);
            writingArea.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            choiceContainer.addView(writingArea);
        } else if (type.equals("tof")) {
            setFabWithText("O");
            setFabWithText("X");
        } else if (type.equals("m")) {
            FeelingMutipleChoices mc = (FeelingMutipleChoices) feeling;
            ArrayList<String> options = mc.getOptions();
            for (int i = 0; i < options.size(); i++) {
                setFabWithText(i + 1 + "");
            }
        }
    }

    private int selectedAns;

    public void setFabWithText(String text) {
        final String innerContent = text;
        final View fabWithText = LayoutInflater.from(GetFeelingsActivity.this).inflate(R.layout.fab_with_text, null);
        com.github.clans.fab.FloatingActionButton button = (com.github.clans.fab.FloatingActionButton) fabWithText.findViewById(R.id.button);
        fabWithText.setTag(text);
        fabWithText.setClickable(true);
        Log.d("setFabWithTextSetId", fabWithText.getTag() + " " + fabWithText.getId());
        TextView textView = (TextView) fabWithText.findViewById(R.id.fab_with_text_textview);
        textView.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (innerContent) {
                    case "1":
                        selectedAns = 1;
                        break;
                    case "2":
                        selectedAns = 2;
                        break;
                    case "3":
                        selectedAns = 3;
                        break;
                    case "4":
                        selectedAns = 4;
                        break;
                    case "O":
                        selectedAns = 1;
                        break;
                    case "X":
                        selectedAns = 0;
                        break;
                }
            }
        });
        choiceContainer.addView(fabWithText);
    }

    public void initResponceComponent() {

        replyPanle = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_get_feelings_reply_slide_up_panel, null);
        slideUpView.addView(replyPanle);

        titleBar = (LinearLayout) findViewById(R.id.titlebar);
        titleBar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (slideUp.isVisible()) {
                    if (!keyBoardIsShown) {
                        titleBar.setVisibility(View.INVISIBLE);
                        keyBoardIsShown = true;
                    } else {
                        titleBar.setVisibility(View.VISIBLE);
                        keyBoardIsShown = false;
                    }
                }
            }
        });
        sendButton = (ImageView) replyPanle.findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String type = mCardAdapter.getItem(position).getType();
                int writerCorrectAns = -1;
                String writerCorrectRea = "";
                Log.d("choicedAns", selectedAns + "");
                if (type.equals("tof")) {
                    FeelingTureOrFalse tof = (FeelingTureOrFalse) mCardAdapter.getItem(position);

                    writerCorrectAns = tof.getChoiceImpl().getCorrectNum();
                    writerCorrectRea = tof.getChoiceImpl().getReason();

                } else if (type.equals("m")) {

                    FeelingMutipleChoices fmc = (FeelingMutipleChoices) mCardAdapter.getItem(position);

                    writerCorrectAns = fmc.getChoiceImpl().getCorrectNum();
                    writerCorrectRea = fmc.getChoiceImpl().getReason();

                }

                if (type.equals("f")) {
                } else if (type.equals("tof")) {
                    if (writerCorrectAns == selectedAns) {
                        SweetAlertDialog dialog = new SweetAlertDialog(GetFeelingsActivity.this, SweetAlertDialog.REPLAY_DIALOG_CORRECT);
                        dialog.setCorrectReason(writerCorrectRea).show();
                    } else {
                        final String correctanstext;
                        if (writerCorrectAns == 1) {
                            correctanstext = "O";
                            Log.d("correctanstext", writerCorrectAns + "  " + correctanstext);
                        } else {
                            correctanstext = "X";
                        }
                        SweetAlertDialog dialog = new SweetAlertDialog(GetFeelingsActivity.this, SweetAlertDialog.REPLAY_DIALOG_WRONG);
                        dialog.setCorrectReason(writerCorrectRea)
                                .setCorrectAns(correctanstext)
                                .show();
                    }
                } else if (type.equals("m")) {
                    if (selectedAns == writerCorrectAns) {
                        new SweetAlertDialog(GetFeelingsActivity.this, SweetAlertDialog.REPLAY_DIALOG_CORRECT)
                                .setCorrectReason(writerCorrectRea)
                                .show();
                    } else {
                        new SweetAlertDialog(GetFeelingsActivity.this, SweetAlertDialog.REPLAY_DIALOG_WRONG)
                                .setCorrectReason(writerCorrectRea)
                                .setCorrectAns(writerCorrectAns + "")
                                .show();
                    }
                }

            }
        });

        choiceContainer = (LinearLayout) replyPanle.findViewById(R.id.choice_container);
        inputEditText = (EditText) replyPanle.findViewById(R.id.inputEditText);
    }

    public ArrayList<String> getFeelingMutipleChoicesOptions() {
        ArrayList<String> data = new ArrayList<>();
        data.add("不愛");
        data.add("愛");
        data.add("愛你媽");
        data.add("愛你爸");
        return data;
    }

    @Override
    public void onBackPressed() {
        if (slideUp.isVisible()) {
            slideUp.hide();
        } else {
            super.onBackPressed();
        }
    }

    public void dragCard() {
        mCardStack.dragListener.onDragStartByButton(38, -5);
        OnThread();
    }

    public void OnThread() {
        final int time = 6;
        mCardStack.dragListener.onDragContinueByButton(51, -8);
        mCardStack.dragListener.onDragContinueByButton(66, -11);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCardStack.dragListener.onDragContinueByButton(97, -15);
                mCardStack.dragListener.onDragContinueByButton(123, -15);
                mCardStack.dragListener.onDragContinueByButton(133, -15);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCardStack.dragListener.onDragContinueByButton(142, -14);
                        mCardStack.dragListener.onDragContinueByButton(155, -12);
                        mCardStack.dragListener.onDragContinueByButton(166, -11);
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCardStack.dragListener.onDragContinueByButton(167, -10);
                                mCardStack.dragListener.onDragContinueByButton(176, -9);
                                mCardStack.dragListener.onDragContinueByButton(185, -8);
                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCardStack.dragListener.onDragContinueByButton(194, -7);
                                        mCardStack.dragListener.onDragContinueByButton(206, -4);
                                        mCardStack.dragListener.onDragContinueByButton(219, -1);
                                        Handler h = new Handler();
                                        h.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mCardStack.dragListener.onDragContinueByButton(245, 5);
                                                mCardStack.dragListener.onDragContinueByButton(275, 17);
                                                mCardStack.dragListener.onDragContinueByButton(309, -32);
                                                Handler h = new Handler();
                                                h.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mCardStack.dragListener.onDragContinueByButton(341, 49);
                                                        mCardStack.dragListener.onDragContinueByButton(368, 66);
                                                        mCardStack.dragListener.onDragContinueByButton(392, 84);
                                                        Handler h = new Handler();
                                                        h.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                mCardStack.dragListener.onDragContinueByButton(411, 100);
                                                                mCardStack.dragListener.onDragContinueByButton(432, 116);
                                                                mCardStack.dragListener.onDragContinueByButton(449, 130);
                                                                Handler h = new Handler();
                                                                h.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        mCardStack.dragListener.onDragContinueByButton(464, 140);
                                                                        mCardStack.dragListener.onDragContinueByButton(475, 150);
                                                                        mCardStack.dragListener.onDragEndByButton();
                                                                    }
                                                                }, time);
                                                            }
                                                        }, time);
                                                    }
                                                }, time);
                                            }
                                        }, time);
                                    }
                                }, time);
                            }
                        }, time);
                    }
                }, time);
            }
        }, time);
    }
}
