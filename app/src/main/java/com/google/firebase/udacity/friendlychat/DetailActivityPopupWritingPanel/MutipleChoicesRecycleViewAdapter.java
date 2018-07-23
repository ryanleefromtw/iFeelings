package com.google.firebase.udacity.friendlychat.DetailActivityPopupWritingPanel;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.udacity.friendlychat.R;

import java.util.ArrayList;

/**
 * Created by X220 on 2017/8/8.
 */

public class MutipleChoicesRecycleViewAdapter extends RecyclerView.Adapter<MutipleChoicesRecycleViewAdapter.ViewHolder> {

    public static final String ON_TOUCHPOS = "onTouchpos";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText editText;
        public ImageButton deleteButton;
        boolean added;
        boolean removed;
        boolean isDeletedByButton = false;

        public ViewHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.editText);
            deleteButton = (ImageButton) itemView.findViewById(R.id.cancelButton);
        }
    }

    GestureDetector gestureDetector;
    private Context context;
    ArrayList<String> data;

    public MutipleChoicesRecycleViewAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                holder.editText.setTextColor(Color.parseColor("#f10404"));
                holder.editText.setText(holder.editText.getText().toString());
                Log.d("onDoubleTap", holder.getAdapterPosition() + "");
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    public MutipleChoicesRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.mutiple_choices_listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    ArrayList<EditText> editTexts = new ArrayList<>();

    ViewHolder holder;
    int correctPos = -1;


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        this.holder = holder;
        editTexts.add(holder.editText);
        holder.deleteButton.setVisibility(View.GONE);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClickpos", holder.getAdapterPosition() + "");

                if (correctPos != -1 && holder.getAdapterPosition() < correctPos) {
                    correctPos--;
                } else if (holder.getAdapterPosition() == correctPos) {
                    correctPos = -1;
                }

                editTexts.remove(holder.editText);
                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                holder.isDeletedByButton = true;

            }
        });

        holder.deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (correctPos != -1) {
                    editTexts.get(correctPos).setTextColor(Color.parseColor("#000000"));
                }
                holder.editText.setTextColor(Color.parseColor("#f10404"));
                correctPos = holder.getAdapterPosition();
                return true;
            }
        });

        holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("onFocusChange", v.getId() + "");
                EditText editText = (EditText) v;
                if (!hasFocus) {
                    if (editText.getText().toString().length() < 1 && holder.getAdapterPosition() != getItemCount() - 1 && !holder.isDeletedByButton) {
                        data.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                } else if (hasFocus) {
                    Log.d("view id", v.getId() + "");
                }
            }
        });

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (start == 0 && after > 1 && !holder.added) {
                    data.add(holder.getAdapterPosition() + 1, getItemCount() + "");
                    notifyItemInserted(getItemCount() - 1 + 1);
                    holder.added = true;
                    holder.removed = false;
                    holder.deleteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < editTexts.size(); i++) {
            String content = editTexts.get(i).getText().toString();
            if (!content.matches(""))
                data.add(editTexts.get(i).getText().toString());
        }
        data.add(correctPos + "");
        return data;
    }


}
