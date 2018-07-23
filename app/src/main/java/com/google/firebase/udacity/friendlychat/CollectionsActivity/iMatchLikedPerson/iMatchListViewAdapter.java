package com.google.firebase.udacity.friendlychat.CollectionsActivity.iMatchLikedPerson;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.CollectionsActivity.ReplyCardItem;
import com.google.firebase.udacity.friendlychat.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by X220 on 2017/9/28.
 */

public class iMatchListViewAdapter extends ArrayAdapter<iMatchListViewItem> {


    Context context;

    public iMatchListViewAdapter(Context context, List<iMatchListViewItem> items) {
        super(context, 0, items);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        viewholder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.imatchlistviewitem, parent, false);
            viewholder = new viewholder();
            viewholder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            viewholder.profileImageView = (CircleImageView) convertView.findViewById(R.id.profile_imageView);
            viewholder.percentageTextView = (TextView) convertView.findViewById(R.id.percentageTextView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (viewholder) convertView.getTag();
        }

        iMatchListViewItem item = getItem(position);
        viewholder.nameTextView.setText(item.getName());
        viewholder.profileImageView.setImageBitmap(item.getProfilePic());
        viewholder.percentageTextView.setText(item.getPercentage());

        return convertView;
    }


    class viewholder {
        TextView nameTextView;
        ImageView profileImageView;
        TextView percentageTextView;
    }

}
