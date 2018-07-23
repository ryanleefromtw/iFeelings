package com.google.firebase.udacity.friendlychat.CollectionFragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.udacity.friendlychat.HomeActivity;
import com.google.firebase.udacity.friendlychat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class iMatchFragment extends Fragment {


    public iMatchFragment() {
        // Required empty public constructor
    }

    FrameLayout popUpNameCard, backGround;
    LinearLayout firstTestLinearLayout;
    ImageView cancleButton;
    Context context;
    public static final String fragmentTag = "collections_iMatch";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_i_match, container, false);
        firstTestLinearLayout = (LinearLayout) rootLayout.findViewById(R.id.firstTestLinearLayout);
        popUpNameCard = (FrameLayout) rootLayout.findViewById(R.id.popupNameCard);
        cancleButton = (ImageView) popUpNameCard.findViewById(R.id.closeButton);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpNameCard.setVisibility(View.GONE);
                backGround.setVisibility(View.GONE);
            }
        });
        backGround = (FrameLayout) rootLayout.findViewById(R.id.background);

        firstTestLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpNameCard.setVisibility(View.VISIBLE);
                backGround.setVisibility(View.VISIBLE);
            }
        });

        ((HomeActivity) context).titleTextView.setText("iMatch");
        ((HomeActivity) context).titleBackimageView.setVisibility(View.VISIBLE);


        return rootLayout;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}
