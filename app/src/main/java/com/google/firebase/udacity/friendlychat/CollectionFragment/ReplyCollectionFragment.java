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
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.firebase.udacity.friendlychat.HomeActivity;
import com.google.firebase.udacity.friendlychat.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReplyCollectionFragment extends Fragment {

    public ReplyCollectionFragment() {
        // Required empty public constructor
    }

    FrameLayout backgroundFrameLayout, popUpReplyDetail;
    LinearLayout testLinerLayout;
    ImageView closeButton;
    public static final String fragmentTag = "collections_ReplyFromOther";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reply_collection, container, false);
        backgroundFrameLayout = (FrameLayout) rootView.findViewById(R.id.background);
        popUpReplyDetail = (FrameLayout) rootView.findViewById(R.id.popup_reply_detail);
        closeButton = (ImageView) popUpReplyDetail.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundFrameLayout.setVisibility(View.GONE);
                popUpReplyDetail.setVisibility(View.GONE);
            }
        });
        testLinerLayout = (LinearLayout) rootView.findViewById(R.id.testLinearLayout);
        testLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundFrameLayout.setVisibility(View.VISIBLE);
                popUpReplyDetail.setVisibility(View.VISIBLE);
            }
        });
        ((HomeActivity) context).titleTextView.setText("回覆");
        ((HomeActivity) context).titleBackimageView.setVisibility(View.VISIBLE);

        return rootView;
    }

    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}
