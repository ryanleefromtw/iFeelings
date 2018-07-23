package com.google.firebase.udacity.friendlychat.CollectionsActivity.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by X220 on 2017/9/13.
 */

public class CommentsListAdapter extends ArrayAdapter<CommentItem> {

    public CommentsListAdapter(Context context, List<CommentItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CommentItem item = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View commentView = inflater.inflate(R.layout.reply_content_item, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mProfilePicImageView = (ImageView) commentView.findViewById(R.id.iV_content_item_profilepic);
        viewHolder.mNameTextView = (TextView)commentView.findViewById(R.id.tV_content_item_name);
        viewHolder.mContentTextView = (TextView)commentView.findViewById(R.id.tV_reply_content_content);

        viewHolder.mProfilePicImageView.setImageBitmap(item.getmProfilePic());
        viewHolder.mNameTextView.setText(item.getmAuthorName());
        viewHolder.mContentTextView.setText(item.getmContent());

        return commentView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public static class ViewHolder {
        ImageView mProfilePicImageView;
        TextView mNameTextView;
        TextView mContentTextView;
    }
}

