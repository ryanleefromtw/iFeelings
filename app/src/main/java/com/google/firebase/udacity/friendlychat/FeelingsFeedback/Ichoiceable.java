package com.google.firebase.udacity.friendlychat.FeelingsFeedback;

/**
 * Created by X220 on 2017/8/18.
 */

public interface Ichoiceable {
    void setReason(String reason);
    String getReason();
    void setCorrectNum(int CNum);
    int getCorrectNum();
}
