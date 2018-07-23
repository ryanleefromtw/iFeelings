package com.google.firebase.udacity.friendlychat.FeelingsFeedback;

/**
 * Created by X220 on 2017/8/18.
 */

public class BaseFeeling {
    String title;
    String type;
    Ichoiceable ichoiceable;

    public BaseFeeling(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public BaseFeeling(String title, String type, Ichoiceable ichoiceable) {
        this.title = title;
        this.type = type;
        this.ichoiceable = ichoiceable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Ichoiceable getIchoiceable() {
        return ichoiceable;
    }

    public void setIchoiceable(Ichoiceable ichoiceable) {
        this.ichoiceable = ichoiceable;
    }
}
