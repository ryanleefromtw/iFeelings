package com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.google.firebase.udacity.friendlychat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrueOrFalseFeelingsFragment extends Fragment {


    public TrueOrFalseFeelingsFragment() {
        // Required empty public constructor
    }

    EditText editText;
    ImageView btnTrue, btnFalse;
    TextView next;

    public void setNext(TextView textView) {
        this.next = textView;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer == 0) {
                    AlertDialog dialog;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("您沒有設定答案")
                            .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialog = builder.create();
                    dialog.show();
                } else {
                    String question = editText.getText().toString();
                    Log.d("TorFQ", question + "  " + correctAnswer);
                }
            }
        });
    }

    int correctAnswer = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_true_or_false_feelings, container, false);
        btnTrue = (ImageView) view.findViewById(R.id.btnTrue);
        btnFalse = (ImageView) view.findViewById(R.id.btnFalse);
        editText = (EditText) view.findViewById(R.id.editTextQuestion);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer == -1) {
                    btnFalse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_curve));
                }
                btnTrue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.selected_button_curve));
                correctAnswer = 1;
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer == 1) {
                    btnTrue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_curve));
                }
                btnFalse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.selected_button_curve));
                correctAnswer = -1;
            }
        });
        return view;
    }
}
