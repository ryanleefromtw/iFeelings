package com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MutipleChoicesFeelingsFragment extends Fragment {

    TextView next;
    public MutipleChoicesFeelingsFragment() {
    }

    public void setNext(final TextView next) {
        this.next = next;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.requestFocus();
                Log.d("dataToShow",adapter.getData().toString());
            }
        });
    }

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MutipleChoicesRecycleViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mutiple_choices_feelings, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
        ArrayList<String> data = new ArrayList<>();
        data.add("0");


       adapter = new MutipleChoicesRecycleViewAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);

        return view;
    }

}