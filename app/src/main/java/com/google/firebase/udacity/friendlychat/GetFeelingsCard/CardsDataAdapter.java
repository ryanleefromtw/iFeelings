package com.google.firebase.udacity.friendlychat.GetFeelingsCard;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.FeelingsFeedback.BaseFeeling;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingMutipleChoices;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingPure;
import com.google.firebase.udacity.friendlychat.FeelingsFeedback.FeelingTureOrFalse;
import com.google.firebase.udacity.friendlychat.IndoorMap;
import com.google.firebase.udacity.friendlychat.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.jar.Attributes;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by X220 on 2017/7/31.
 */

public class CardsDataAdapter extends ArrayAdapter<BaseFeeling> {
    Context context;
    FrameLayout popupNameCard, progrssBarAndCheckedIVFrameLayout;


    public CardsDataAdapter(Context context, FrameLayout popupNameCard) {
        super(context, R.layout.card_content);
        this.context = context;
        this.popupNameCard = popupNameCard;
    }

    TextView NameCardCancle, NameCardSend , successSendTextView;
    ProgressBar progressBar;
    LinearLayout checkImageView;
    ImageView finishCrossImageView;
    CircleImageView profileCircleImageView;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View contentView, @NonNull ViewGroup parent) {

        BaseFeeling feeling = getItem(position);
        String type = feeling.getType();

        TextView titleTextView, authorTextView, likeNumTextView;
        titleTextView = (TextView) contentView.findViewById(R.id.textViewCardTitle);
        authorTextView = (TextView) contentView.findViewById(R.id.authorTextView);
        profileCircleImageView = (CircleImageView) contentView.findViewById(R.id.profile_image);
        likeNumTextView = (TextView) contentView.findViewById(R.id.likeNumTextView);
        FrameLayout frameLayout;
        frameLayout = (FrameLayout) contentView.findViewById(R.id.frameLayoutContainer);


        progressBar = (ProgressBar) popupNameCard.findViewById(R.id.sendNameCardProgressBar);
        NameCardSend = (TextView) popupNameCard.findViewById(R.id.sendNameCardTextView);
        successSendTextView = (TextView)popupNameCard.findViewById(R.id.textviewsuccessend);
        finishCrossImageView = (ImageView) popupNameCard.findViewById(R.id.nameCardsuccessCancel);
        finishCrossImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupNameCard.setVisibility(View.GONE);
            }
        });
        checkImageView = (LinearLayout) popupNameCard.findViewById(R.id.successImageView);

        progrssBarAndCheckedIVFrameLayout = (FrameLayout) popupNameCard.findViewById(R.id.progressBarANDCheckedIVFrameLayout);
        NameCardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progrssBarAndCheckedIVFrameLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        checkImageView.setVisibility(View.VISIBLE);
                        finishCrossImageView.setVisibility(View.VISIBLE);
                    }
                }, 800);
            }
        });
        NameCardCancle = (TextView) popupNameCard.findViewById(R.id.cancelTextView);
        NameCardCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupNameCard.setVisibility(View.GONE);
            }
        });


        if (type.equals("f")) {
            final FeelingPure feelingPure = (FeelingPure) feeling;
            titleTextView.setText(feelingPure.getTitle());
            authorTextView.setText(feelingPure.getAuthorName());
            TextView textView = new TextView(context);
            textView.setMaxLines(11);
            textView.setVerticalScrollBarEnabled(true);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setTextSize(20);
            textView.setText(feelingPure.getFeeling());
            frameLayout.addView(textView);

        } else if (type.equals("tof")) {
            titleTextView.setText("是非題");
            FeelingTureOrFalse tof = (FeelingTureOrFalse) feeling;

            TextView textViewQuestion = new TextView(context);
            textViewQuestion.setTextSize(30);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(12, 12, 12, 12);
            textViewQuestion.setLayoutParams(llp);
            textViewQuestion.setTextColor(Color.parseColor("#000000"));
            textViewQuestion.setText(tof.getTitle().toString());

            frameLayout.addView(textViewQuestion);

        } else if (type.equals("m")) {
            final FeelingMutipleChoices mc = (FeelingMutipleChoices) feeling;

            titleTextView.setText(mc.getTitle());

            authorTextView.setText(mc.getAuthorName());

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final ArrayList<String> options = mc.getOptions();
            final ArrayList<LinearLayout> optionsLinearLayoutView = new ArrayList<>();
            LinearLayout choicesLayoutContainer = (LinearLayout) inflater.inflate(R.layout.choices_linear_layout_container, null);
            for (int i = 0; i < options.size(); i++) {
                if(i == 1){
                    profileCircleImageView.setImageDrawable(getContext().getDrawable(R.drawable.head3));
                }
                final LinearLayout optionsLayout = (LinearLayout) inflater.inflate(R.layout.card_content_choices, null);
                Log.d("optionsLayout", optionsLayout.toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));

                if (i != 0)
                    lp.setMargins(0, 46, 0, 0);

                optionsLayout.setLayoutParams(lp);

                TextView optionTextView = (TextView) optionsLayout.findViewById(R.id.optionTextView);
                optionTextView.setText(options.get(i));
                final TextView choice = (TextView) optionsLayout.findViewById(R.id.optionTextView);

                optionsLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsLayout.setBackground(context.getResources().getDrawable(R.drawable.options_button_background_selected));
                        optionsLinearLayoutView.get(mc.getCorrectAns()).setBackground(context.getResources().getDrawable(R.drawable.options_button_background_correct_ans));
                        if (optionsLinearLayoutView.indexOf(optionsLayout) == mc.getCorrectAns()) {
                            popupNameCard.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < optionsLinearLayoutView.size(); i++) {
                            optionsLinearLayoutView.get(i).setClickable(false);
                        }
                    }
                });

                choice.setText(options.get(i));
                choicesLayoutContainer.addView(optionsLayout);
                optionsLinearLayoutView.add(optionsLayout);
                Log.d("option", options.get(i));
            }
            frameLayout.addView(choicesLayoutContainer);
        }
        return contentView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
