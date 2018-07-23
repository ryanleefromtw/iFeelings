package com.google.firebase.udacity.friendlychat.CollectionsActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.udacity.friendlychat.CollectionsActivity.comment.WrapHeightListView;
import com.google.firebase.udacity.friendlychat.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

/**
 * Created by X220 on 2017/9/12.
 */

public class ReplyCardFoldingCellAdapter extends ArrayAdapter<ReplyCardItem> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private Context context;

    public ReplyCardFoldingCellAdapter(Context context, List<ReplyCardItem> items) {
        super(context, 0, items);
        this.context = context;
    }

    FoldingCell cell;

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        ReplyCardItem item = getItem(position);
        cell = (FoldingCell) convertView;
        ViewHolder viewHolder;


        if (cell == null) {
            viewHolder = new ViewHolder();

            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.reply_card_cell, parent, false);

            viewHolder.mQuesTitleTextView = (TextView) cell.findViewById(R.id.tV_cell_title_title);
            viewHolder.mTitleObjImageView = (ImageView) cell.findViewById(R.id.iV_cell_title_pic);
            viewHolder.mHasNewLabelTriangleLabelView = (TriangleLabelView) cell.findViewById(R.id.triangleLV_cell_title_newReply);
            viewHolder.mContentObjImageView = (ImageView) cell.findViewById(R.id.iV_cell_content_head_image);
            viewHolder.mCloseButton = (ImageView) cell.findViewById(R.id.iV_cell_content_close);
            viewHolder.mReplyNumTextView = (TextView) cell.findViewById(R.id.tV_cell_content_reply_num);
            viewHolder.mLikedNumTextView = (TextView) cell.findViewById(R.id.tV_cell_content_liked_num);
            viewHolder.mCollectsNumTextView = (TextView) cell.findViewById(R.id.tV_cell_content_collect_num);
//            viewHolder.mCommandsContainer = (WrapHeightListView) cell.findViewById(R.id.lV_cell_content_comment_container);
            viewHolder.frameLayout = (FrameLayout) cell.findViewById(R.id.container);
            cell.setTag(viewHolder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }
        //title cell
        viewHolder.mQuesTitleTextView.setText(item.getmTitle());
        viewHolder.mTitleObjImageView.setImageBitmap(item.getmObjectPic());

        if (item.getmHasNewReply()) {
            viewHolder.mHasNewLabelTriangleLabelView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mHasNewLabelTriangleLabelView.setVisibility(View.GONE);
        }

        //content cell
        viewHolder.mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("closeButton", "getcalled" + position);
                cell.toggle(true);
                registerToggle(position);
            }
        });
        viewHolder.mContentObjImageView.setImageBitmap(item.getmObjectPic());
        viewHolder.mLikedNumTextView.setText(item.getmLikedNum() + "");
        viewHolder.mReplyNumTextView.setText(item.getmReplyNum() + "");
        viewHolder.mCollectsNumTextView.setText(item.getmCollectNum() + "");
//        viewHolder.mCommandsContainer.setAdapter(item.getmCommentsDataAdapter());

        viewHolder.frameLayout.removeAllViews();
        viewHolder.frameLayout.addView(item.getmLinearLayout());
        return cell;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    private static class ViewHolder {
        TextView mQuesTitleTextView;
        ImageView mTitleObjImageView;
        ImageView mContentObjImageView;
        TriangleLabelView mHasNewLabelTriangleLabelView;
        ImageView mCloseButton;
        TextView mReplyNumTextView;
        TextView mLikedNumTextView;
        TextView mCollectsNumTextView;
        WrapHeightListView mCommandsContainer;
        FrameLayout frameLayout;
    }
}
