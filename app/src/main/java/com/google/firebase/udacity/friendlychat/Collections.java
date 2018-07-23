package com.google.firebase.udacity.friendlychat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.udacity.friendlychat.CollectionFragment.ArtCollectionFragment;
import com.google.firebase.udacity.friendlychat.CollectionFragment.NameCardCollectionFragment;
import com.google.firebase.udacity.friendlychat.CollectionFragment.ReplyCollectionFragment;
import com.google.firebase.udacity.friendlychat.CollectionFragment.iMatchFragment;
import com.google.firebase.udacity.friendlychat.CollectionsActivity.ArtCollectionsActivity;
import com.google.firebase.udacity.friendlychat.CollectionsActivity.ReplyFromOtherActivity;
import com.google.firebase.udacity.friendlychat.CollectionsActivity.iMatchActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Collections extends Fragment {

    public Collections() {
        // Required empty public constructor
    }

    LinearLayout iMatch;
    LinearLayout mReplyFromOther;
    LinearLayout mArtCollections;
    LinearLayout mReplyCollections;
    LinearLayout mGotNameCard;
    LinearLayout mReplyHistory;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == true)
            ((HomeActivity) context).lastVisibleFragmentTag = fragmentTag;
    }

    public static final String fragmentTag = "Collections";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_collections, container, false);
//
//        mReplyFromOther = (LinearLayout) rootView.findViewById(R.id.reply_from_other);
//        mReplyFromOther.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ReplyFromOtherActivity.class));
//            }
//        });
//        mArtCollections = (LinearLayout) rootView.findViewById(R.id.collections_art);
//        mArtCollections.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ArtCollectionsActivity.class));
//            }
//        });
//        mReplyCollections = (LinearLayout) rootView.findViewById(R.id.collections_reply);
//        mGotNameCard = (LinearLayout) rootView.findViewById(R.id.got_namecard);
//        mReplyHistory = (LinearLayout) rootView.findViewById(R.id.reply_history);
//
//        iMatch = (LinearLayout) rootView.findViewById(R.id.match);
//        iMatch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), iMatchActivity.class));
//            }
//        });

        iMatch = (LinearLayout) rootView.findViewById(R.id.iMatchLinearLayout);
        iMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMatchFragment iMatchFragment = new iMatchFragment();
                ((HomeActivity) context).changeFragment(iMatchFragment.fragmentTag, iMatchFragment);
            }
        });

        mGotNameCard = (LinearLayout) rootView.findViewById(R.id.nameCardCollection);
        mGotNameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameCardCollectionFragment nameCardCollectionFragment = new NameCardCollectionFragment();
                ((HomeActivity) context).changeFragment(NameCardCollectionFragment.fragmentTag, nameCardCollectionFragment);
            }
        });

        mReplyFromOther = (LinearLayout)rootView.findViewById(R.id.linearLayoutGetReply);
        mReplyFromOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplyCollectionFragment replyCollectionFragment = new ReplyCollectionFragment();
                ((HomeActivity) context).changeFragment(ReplyCollectionFragment.fragmentTag, replyCollectionFragment);
            }
        });

        mArtCollections = (LinearLayout)rootView.findViewById(R.id.artcollection);
        mArtCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArtCollectionFragment artCollectionFragment = new ArtCollectionFragment();
                ((HomeActivity) context).changeFragment(com.google.firebase.udacity.friendlychat.CollectionFragment.ArtCollectionFragment.fragmentTag, artCollectionFragment);
                ((HomeActivity) context).titleTextView.setText("展品收藏");
                ((HomeActivity) context).titleBackimageView.setVisibility(View.VISIBLE);
            }
        });



        return rootView;
    }

    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = (HomeActivity) activity;
    }
}
