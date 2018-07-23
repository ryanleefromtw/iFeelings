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
public class NameCardCollectionFragment extends Fragment {

    public NameCardCollectionFragment() {
        // Required empty public constructor
    }

    public static final String fragmentTag = "collections_NameCard";
    FrameLayout backGroundFrameLayout;
    LinearLayout TestLinearLayout;
    ImageView profileImageButton;
    FrameLayout nameCard;
    ImageView closeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_name_card_collection, container, false);
        TestLinearLayout = (LinearLayout) rootView.findViewById(R.id.testLinerLayout);
        profileImageButton = (ImageView) TestLinearLayout.findViewById(R.id.profileImageButton);
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backGroundFrameLayout.setVisibility(View.VISIBLE);
                    nameCard.setVisibility(View.VISIBLE);
            }
        });
        nameCard = (FrameLayout) rootView.findViewById(R.id.popupNameCard);
        closeButton = (ImageView) nameCard.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backGroundFrameLayout.setVisibility(View.GONE);
                nameCard.setVisibility(View.GONE);

            }
        });
        ((HomeActivity) context).titleTextView.setText("名片收藏");
        ((HomeActivity) context).titleBackimageView.setVisibility(View.VISIBLE);
        backGroundFrameLayout = (FrameLayout) rootView.findViewById(R.id.background);
        return rootView;
    }

    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = (HomeActivity) activity;
    }
}
