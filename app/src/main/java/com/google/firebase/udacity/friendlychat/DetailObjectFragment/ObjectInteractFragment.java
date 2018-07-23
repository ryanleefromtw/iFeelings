package com.google.firebase.udacity.friendlychat.DetailObjectFragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingMutipleChoices;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingPure;
import com.google.firebase.udacity.friendlychat.GetFeelingsCard.CardsDataAdapter;
import com.google.firebase.udacity.friendlychat.R;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.wenchao.cardstack.CardAnimator;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObjectInteractFragment extends Fragment {


    public ObjectInteractFragment() {
        // Required empty public constructor
    }

    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    private com.github.clans.fab.FloatingActionButton mfabNext, mfabWrite;

    private LinearLayout slideUpView;
    private SlideUp slideUp;
    private CardView replyPanle;
    private EditText replyEditText;
    private FrameLayout popUpNameCardLinearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_object_interact, container, false);

        mfabNext = (com.github.clans.fab.FloatingActionButton) layout.findViewById(R.id.fab_next);
        mfabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragCard();
            }
        });

        mfabWrite = (FloatingActionButton) layout.findViewById(R.id.fab_edit);
        mfabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp.show();
            }
        });

        mCardStack = (CardStack) layout.findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setStackGravity(CardAnimator.BOTTOM);
        mCardStack.setStackMargin(15);

        popUpNameCardLinearLayout = (FrameLayout) layout.findViewById(R.id.popupNameCard);

        mCardAdapter = new CardsDataAdapter(getActivity(), popUpNameCardLinearLayout);
        mCardAdapter.add(new FeelingPure("某年初夏", "f", getResources().getString(R.string.description3), "Owen"));
        mCardAdapter.add(new FeelingMutipleChoices("你覺得蒙娜麗莎幾歲?", "m", "爽", 3, getFeelingMutipleChoicesOptions(), "Howard"));

        mCardStack.setAdapter(mCardAdapter);

        slideUpView = (LinearLayout) layout.findViewById(R.id.feedback_slide_up_view);

        replyPanle = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.activity_get_feelings_reply_slide_up_panel, null);
        slideUpView.addView(replyPanle);

        replyEditText = (EditText) replyPanle.findViewById(R.id.inputEditText);

        slideUp = new SlideUpBuilder(slideUpView)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
        slideUp.setAutoSlideDuration(500);

        return layout;
    }

    public ArrayList<String> getFeelingMutipleChoicesOptions() {
        ArrayList<String> data = new ArrayList<>();
        data.add("32");
        data.add("33");
        data.add("69");
        data.add("72");
        return data;
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
