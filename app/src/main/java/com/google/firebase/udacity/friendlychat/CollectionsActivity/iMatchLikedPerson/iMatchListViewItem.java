package com.google.firebase.udacity.friendlychat.CollectionsActivity.iMatchLikedPerson;

import android.graphics.Bitmap;

/**
 * Created by X220 on 2017/9/28.
 */

public class iMatchListViewItem {
    private Bitmap profilePic;
    private String name;
    private String percentage;

    public iMatchListViewItem(Bitmap profilePic, String name, String percentage) {
        this.profilePic = profilePic;
        this.name = name;
        this.percentage = percentage;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Bitmap profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
