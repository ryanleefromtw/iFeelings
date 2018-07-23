package com.google.firebase.udacity.friendlychat.CollectionsActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.udacity.friendlychat.CollectionsActivity.iMatchLikedPerson.iMatchListViewAdapter;
import com.google.firebase.udacity.friendlychat.CollectionsActivity.iMatchLikedPerson.iMatchListViewItem;
import com.google.firebase.udacity.friendlychat.R;

import java.util.ArrayList;
import java.util.List;

public class iMatchActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_match);
        listView = (ListView) findViewById(R.id.imatch_listView);
        iMatchListViewAdapter adapter = new iMatchListViewAdapter(getApplicationContext(), getMockData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public List<iMatchListViewItem> getMockData() {
        List<iMatchListViewItem> list = new ArrayList<>();
        for(int i = 0 ; i <10;i++){
            list.add(new iMatchListViewItem(getBitmap(R.drawable.the_girl),"李宜儒",(100-i)+"%"));
        }
        return list;
    }

    public  Bitmap getBitmap(int resource) {
        return BitmapFactory.decodeResource(getResources(), resource);
    }
}
