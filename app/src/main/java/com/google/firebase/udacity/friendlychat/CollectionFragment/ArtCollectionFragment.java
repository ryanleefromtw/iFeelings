package com.google.firebase.udacity.friendlychat.CollectionFragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.udacity.friendlychat.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtCollectionFragment extends Fragment {


    public ArtCollectionFragment() {
        // Required empty public constructor
    }

    LinearLayout linearLayout;
    public static final String fragmentTag = "collections_Art";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_art_collection, container, false);

        inflater.inflate(R.layout.fragment_art_collection, container, false);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);

        CardView view2 = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.art_collections_image_view, null);
        view2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView imageView3 = (ImageView) view2.findViewById(R.id.image_test);
        imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.the_girl));

        CardView view = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.art_collections_image_view, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView imageView2 = (ImageView) view.findViewById(R.id.image_test);
        imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.drawbridge_in_nieuw___amsterdam));
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);


        CardView view3 = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.art_collections_image_view, null);
        view3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView imageView4 = (ImageView) view3.findViewById(R.id.image_test);
        imageView4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.skrik));
        imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);


        linearLayout.addView(view2);
        linearLayout.addView(view);
        linearLayout.addView(view3);
        return rootView;
    }

}
