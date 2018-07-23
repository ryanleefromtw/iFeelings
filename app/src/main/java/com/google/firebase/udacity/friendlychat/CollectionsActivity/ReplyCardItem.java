package com.google.firebase.udacity.friendlychat.CollectionsActivity;

import android.graphics.Bitmap;
import android.widget.LinearLayout;

import com.google.firebase.udacity.friendlychat.CollectionsActivity.comment.CommentsListAdapter;

/**
 * Created by X220 on 2017/9/12.
 */

public class ReplyCardItem {

    private String mTitle;//title
    private int mReplyNum;//content
    private int mLikedNum;//content
    private int mCollectNum;//content
    private CommentsListAdapter mCommentsDataAdapter;//content
    private Bitmap mObjectPic;//content,title
    private Boolean mHasNewReply = false;//title
    private String mArtName;//content
    private LinearLayout mLinearLayout;

    public LinearLayout getmLinearLayout() {
        return mLinearLayout;
    }

    public void setmLinearLayout(LinearLayout mLinearLayout) {
        this.mLinearLayout = mLinearLayout;
    }


    public ReplyCardItem(String mTitle, int mReplyNum, int mLikedNum, int mCollectNum, CommentsListAdapter mCommentsDataAdapter, Bitmap mObjectPic, String mArtName) {
        this.mTitle = mTitle;
        this.mReplyNum = mReplyNum;
        this.mLikedNum = mLikedNum;
        this.mCollectNum = mCollectNum;
        this.mCommentsDataAdapter = mCommentsDataAdapter;
        this.mObjectPic = mObjectPic;
        this.mArtName = mArtName;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmReplyNum() {
        return mReplyNum;
    }

    public void setmReplyNum(int mReplyNum) {
        this.mReplyNum = mReplyNum;
    }

    public int getmLikedNum() {
        return mLikedNum;
    }

    public void setmLikedNum(int mLikedNum) {
        this.mLikedNum = mLikedNum;
    }

    public int getmCollectNum() {
        return mCollectNum;
    }

    public void setmCollectNum(int mCollectNum) {
        this.mCollectNum = mCollectNum;
    }

    public CommentsListAdapter getmCommentsDataAdapter() {
        return mCommentsDataAdapter;
    }

    public void setmCommentsDataAdapter(CommentsListAdapter mCommentsDataAdapter) {
        this.mCommentsDataAdapter = mCommentsDataAdapter;
    }

    public Bitmap getmObjectPic() {
        return mObjectPic;
    }

    public void setmObjectPic(Bitmap mObjectPic) {
        this.mObjectPic = mObjectPic;
    }

    public Boolean getmHasNewReply() {
        return mHasNewReply;
    }

    public void setmHasNewReply(Boolean mHasNewReply) {
        this.mHasNewReply = mHasNewReply;
    }

    public String getmArtName() {
        return mArtName;
    }

    public void setmArtName(String mArtName) {
        this.mArtName = mArtName;
    }
}
