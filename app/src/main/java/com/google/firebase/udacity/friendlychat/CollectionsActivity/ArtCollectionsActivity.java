package com.google.firebase.udacity.friendlychat.CollectionsActivity;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.udacity.friendlychat.R;
import com.yinglan.shadowimageview.ShadowImageView;

public class ArtCollectionsActivity extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_collections);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        CardView view2 = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.art_collections_image_view, null);
        view2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView imageView3 = (ImageView) view2.findViewById(R.id.image_test);
        imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.the_girl));

        CardView view = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.art_collections_image_view, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView imageView2 = (ImageView) view.findViewById(R.id.image_test);
        imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.drawbridge_in_nieuw___amsterdam));
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        linearLayout.addView(view2);
        linearLayout.addView(view);
    }
}
