package com.google.firebase.udacity.friendlychat.cards;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.google.firebase.udacity.friendlychat.R;
import com.google.firebase.udacity.friendlychat.utils.DecodeBitmapTask;

public class SliderCard extends RecyclerView.ViewHolder {

    private static int viewWidth = 0;
    private static int viewHeight = 0;

    public final ImageView imageView;


    private DecodeBitmapTask task;

    public SliderCard(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    void setContent(@DrawableRes final int resId) {
        if (viewWidth == 0) {
            itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    viewWidth = itemView.getWidth();
                    viewHeight = itemView.getHeight();
                    loadBitmap(resId);
                }
            });
        } else {
            loadBitmap(resId);
        }
    }

    void setContent(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    void clearContent() {
        if (task != null) {
            task.cancel(true);
        }
    }

    private void loadBitmap(@DrawableRes int resId) {
        task = new DecodeBitmapTask(itemView.getResources(), resId, viewWidth, viewHeight) {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        };
        task.execute();
    }

}