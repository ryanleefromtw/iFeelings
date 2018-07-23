package com.google.firebase.udacity.friendlychat.FeelingsFeedback;

import java.util.ArrayList;

/**
 * Created by X220 on 2017/8/18.
 */

public class FeelingTureOrFalse extends BaseFeeling {

    ChoiceImpl ChoiceImpl;

    public com.google.firebase.udacity.friendlychat.FeelingsFeedback.ChoiceImpl getChoiceImpl() {
        return ChoiceImpl;
    }

    public void setChoiceImpl(com.google.firebase.udacity.friendlychat.FeelingsFeedback.ChoiceImpl choiceImpl) {
        ChoiceImpl = choiceImpl;
    }

    public FeelingTureOrFalse(String title, String type, int correctAns, String reason) {
        super(title, type);
        ChoiceImpl = new ChoiceImpl(reason, correctAns);
    }

    public FeelingTureOrFalse(String title, String type) {
        super(title, type);
    }

}
