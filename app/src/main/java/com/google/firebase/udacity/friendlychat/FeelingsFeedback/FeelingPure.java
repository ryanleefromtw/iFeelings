package com.google.firebase.udacity.friendlychat.FeelingsFeedback;

/**
 * Created by X220 on 2017/8/18.
 */

public class FeelingPure extends BaseFeeling {

    String feeling;
    String authorName;

    public FeelingPure(String title, String type) {
        super(title, type);
    }

    public FeelingPure(String title, String type, String feeling, String authorName) {
        super(title, type);
        this.feeling = feeling;
        this.authorName = authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }
}
