package com.google.firebase.udacity.friendlychat.CollectionsActivity.comment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by X220 on 2017/9/13.
 */

public class CommentItem {

    public CommentItem(String mAuthorName, String mContent, Bitmap mProfilePic) {
        this.mAuthorName = mAuthorName;
        this.mContent = mContent;
        this.mProfilePic = mProfilePic;
    }

    private String mAuthorName;
    private String mContent;
    private Bitmap mProfilePic;

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public Bitmap getmProfilePic() {
        return mProfilePic;
    }

    public void setmProfilePic(Bitmap mProfilePic) {
        this.mProfilePic = mProfilePic;
    }

}
