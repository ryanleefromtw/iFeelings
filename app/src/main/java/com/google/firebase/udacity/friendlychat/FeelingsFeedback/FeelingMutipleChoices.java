package com.google.firebase.udacity.friendlychat.FeelingsFeedback;

import java.util.ArrayList;

/**
 * Created by X220 on 2017/8/18.
 */

public class FeelingMutipleChoices extends BaseFeeling  {

    ArrayList<String> options;
    ChoiceImpl choiceImpl;
    String authorName;
    int correctAns;

    public FeelingMutipleChoices(String title, String type, String reason, int correctAns, ArrayList<String> options,String authorName) {
        super(title, type);
        choiceImpl = new ChoiceImpl(reason, correctAns);
        this.options = options;
        this.authorName = authorName;
        this.correctAns = correctAns;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public ChoiceImpl getChoiceImpl() {
        return choiceImpl;
    }

    public FeelingMutipleChoices(String title, String type) {
        super(title, type);
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
