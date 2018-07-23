package com.google.firebase.udacity.friendlychat.CollectionsActivity.comment;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * Created by X220 on 2017/9/14.
 */

public class WrapHeightListView extends ListView {

    private android.view.ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public WrapHeightListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (getCount() != oldCount) {
            int height = getChildAt(0).getHeight() + 1;
            oldCount = getCount();
            params = getLayoutParams();
            params.height = getCount() * height;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(params);
        }
        super.onDraw(canvas);
    }
}
