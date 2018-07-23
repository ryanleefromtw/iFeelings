package com.google.firebase.udacity.friendlychat.CollectionsActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.icu.text.Replaceable;
import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.CollectionsActivity.comment.CommentItem;
import com.google.firebase.udacity.friendlychat.CollectionsActivity.comment.CommentsListAdapter;
import com.google.firebase.udacity.friendlychat.CollectionsActivity.comment.WrapHeightListView;
import com.google.firebase.udacity.friendlychat.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ramotion.foldingcell.FoldingCell;
import com.wx.wheelview.graphics.DrawableFactory;

import java.util.ArrayList;
import java.util.List;

public class ReplyFromOtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_from_other);
        ListView theListView = (ListView) findViewById(R.id.mainListView);
        final ReplyCardFoldingCellAdapter replyCardFoldingCellAdapter = new ReplyCardFoldingCellAdapter(this, getTestData());
        theListView.setAdapter(replyCardFoldingCellAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                Log.d("onItemClick1234", pos + "");
                ((FoldingCell) view).toggle(true);
                // register in adapter that state for selected cell is toggled
                replyCardFoldingCellAdapter.registerToggle(pos);
            }
        });
    }

    private List<ReplyCardItem> getTestData() {
        List<ReplyCardItem> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LinearLayout linearLayout = getTestLinearLayout();
            for(int j = 0 ; j <2 ; j ++){
                linearLayout.addView(inflateTestLinearLayoutItem("李宜儒", "hello"+j, getBitmap(R.drawable.ic_launcher)));
            }
            ReplyCardItem cardItem = new ReplyCardItem("樸樹帥嗎?", 1, 2, 3, getTestCommandsListView(), getBitmap(R.drawable.the_girl), "微笑的少女");
            cardItem.setmLinearLayout(linearLayout);
            items.add(cardItem);
        }
        return items;
    }

    private LinearLayout getTestLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }

    private View inflateTestLinearLayoutItem(String name, String content, Bitmap profilePic) {

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.reply_content_item, null);
        TextView tvContent = (TextView) view.findViewById(R.id.tV_reply_content_content);
        tvContent.setText(content);

        TextView tvName = (TextView) view.findViewById(R.id.tV_content_item_name);
        tvName.setText(name);

        ImageView imageView = (ImageView) view.findViewById(R.id.iV_content_item_profilepic);
        imageView.setImageBitmap(profilePic);

        return view;
    }


    private CommentsListAdapter getTestCommandsListView() {

        List<CommentItem> mMockTestData = new ArrayList<>();

        mMockTestData.add(new CommentItem("李宜儒", "hello", getBitmap(R.drawable.ic_launcher)));
        mMockTestData.add(new CommentItem("李宜儒", "hello", getBitmap(R.drawable.ic_launcher)));
        mMockTestData.add(new CommentItem("李宜儒", "hello", getBitmap(R.drawable.ic_launcher)));

        CommentsListAdapter adapter = new CommentsListAdapter(getApplicationContext(), mMockTestData);

        return adapter;
    }

    public  Bitmap getBitmap(int resource) {
        return BitmapFactory.decodeResource(getResources(), resource);
    }
}
