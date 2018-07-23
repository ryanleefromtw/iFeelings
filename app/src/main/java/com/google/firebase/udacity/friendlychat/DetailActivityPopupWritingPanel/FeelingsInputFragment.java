package com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeelingsInputFragment extends Fragment implements View.OnFocusChangeListener {


    public FeelingsInputFragment() {
        // Required empty public constructor
    }

    EditText editText_title, editText_content;
    TextView next;


    public void setNext(TextView next) {
        this.next = next;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editText_title = (EditText) container.findViewById(R.id.editText_title);
        editText_content = (EditText) container.findViewById(R.id.editText_content);
        return inflater.inflate(R.layout.fragment_feelings_input, container, false);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.editText_title:
                editText_title.setHint("");
                break;
            case R.id.editText_content:
                editText_content.setHint("");
                break;
        }
    }
}
