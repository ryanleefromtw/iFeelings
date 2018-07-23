package com.google.firebase.udacity.friendlychat.FeelingsFeedback;

/**
 * Created by X220 on 2017/8/19.
 */

public class ChoiceImpl implements Ichoiceable {

    String reason;
    int correctAns;

    public ChoiceImpl (String reason,int correctAns){
        this.reason = reason;
        this.correctAns = correctAns;
    }


    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public void setCorrectNum(int CNum) {
        this.correctAns = CNum;
    }

    @Override
    public int getCorrectNum() {
        return correctAns;
    }

}
