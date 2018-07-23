package com.google.firebase.udacity.friendlychat;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.udacity.friendlychat.DetailObjectFragment.ObjectDemoFragment;
import com.google.firebase.udacity.friendlychat.FindBeaconService.FindPositionService;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends AppCompatActivity {

    public BottomBar bottomBar;
    FragmentManager fragmentManager;

    FirebaseAuth firebaseAuth;

    private static final String logDebug = "Logger : ";
    private Map FragmentMap;
    private Object FragmentObject;
    private Settings FragmentSettings;
    private Collections FragmentCollections;
    private ObjectDemoFragment FragmentObjectDemo;
    public TextView titleTextView;
    public ImageView titleBackimageView;
    public String nowVisibleFragmentTag;
    public String lastVisibleFragmentTag;
    public TabStateManager tabStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        }
        setContentView(R.layout.activity_home);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getFragments() == null) {
            Log.d(logDebug, "onCrFMisNull" + " " + fragmentManager.toString());
        } else {
            Log.d(logDebug, "onCrFM " + fragmentManager.toString() + " " + fragmentManager.getFragments().toString());
            for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
                fragmentManager.beginTransaction().remove(fragmentManager.getFragments().get(i));
            }
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(AuthUI.EMAIL_PROVIDER, AuthUI.GOOGLE_PROVIDER, AuthUI.FACEBOOK_PROVIDER)
                            .setLogo(R.drawable.logo_text)
                            .setTheme(R.style.LogInTheme)
                            .build(), 1);
                }
            }
        });

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("地圖");
        titleBackimageView = (ImageView) findViewById(R.id.title_arrow_back);
        titleBackimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleBackimageView.setVisibility(View.INVISIBLE);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_Map:
                        setTabSelection(0);
                        titleTextView.setText("地圖");
                        break;
                    case R.id.tab_Object:
                        setTabSelection(1);
                        titleTextView.setText("展品");
                        break;
                    case R.id.tab_collections:
                        setTabSelection(2);
                        titleTextView.setText("收藏");
                        break;
                    case R.id.tab_Settings:
                        setTabSelection(3);
                        titleTextView.setText("帳號");
                        break;
                }
            }
        });

        if (checkBLE())
            enableBLE();

        bindService(new Intent(HomeActivity.this, FindPositionService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        tabStateManager = new TabStateManager();
        tabStateManager.MapState = Map.fragmentTag;
        nowVisibleFragmentTag = Map.fragmentTag;

    }

    BluetoothAdapter mBLEadapter;

    public boolean checkBLE() {
        mBLEadapter = BluetoothAdapter.getDefaultAdapter();
        if (mBLEadapter == null) {
            return false;
        } else {
            return true;
        }
    }

    private static final int BLE_REQUEST_CODE = 2;

    public void enableBLE() {
        if (!mBLEadapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, BLE_REQUEST_CODE);
        }
    }

    public FindPositionService mFindPositionService;
    private boolean mIsFindPositionServiceBound = false;

    public ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("onServiceConnected", "true");
            FindPositionService.MyBinder binder = (FindPositionService.MyBinder) service;
            mFindPositionService = binder.getService();
            mIsFindPositionServiceBound = true;
            mFindPositionService.setCallBack(new FindPositionService.CallBack() {
                @Override
                public void onScanResult(String result) {
                    nowPosition = result;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsFindPositionServiceBound = false;
        }
    };

    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment();
        switch (index) {
            case 0:
                if (fragmentManager.findFragmentByTag("Map") == null) {
                    FragmentMap = Map.newInstance("", "");
                    transaction.add(R.id.contentContainer, FragmentMap, Map.fragmentTag);
                } else {
                    transaction.show(fragmentManager.findFragmentByTag("Map"));
                }
                nowVisibleFragmentTag = Map.fragmentTag;
                break;
            case 1:
                if (fragmentManager.findFragmentByTag("Object") == null) {
                    FragmentObject = Object.newInstance("", "");
                    transaction.add(R.id.contentContainer, FragmentObject, Object.fragmentTag);
                    nowVisibleFragmentTag = "Object";
                } else {
                    transaction.show(fragmentManager.findFragmentByTag(checkTabState(index)));
                    nowVisibleFragmentTag = checkTabState(index);
                }

                break;
            case 2:
                if (fragmentManager.findFragmentByTag("Collections") == null) {
                    FragmentCollections = new Collections();
                    transaction.add(R.id.contentContainer, FragmentCollections, Collections.fragmentTag);
                } else {
                    transaction.show(fragmentManager.findFragmentByTag("Collections"));
                }
                nowVisibleFragmentTag = Collections.fragmentTag;
                break;
            case 3:
                if (fragmentManager.findFragmentByTag("Settings") == null) {
                    FragmentSettings = Settings.newInstance("", "");
                    transaction.add(R.id.contentContainer, FragmentSettings, Settings.fragmentTag);
                } else {
                    transaction.show(fragmentManager.findFragmentByTag("Settings"));
                }
                nowVisibleFragmentTag = Settings.fragmentTag;
                break;
        }
        transaction.commit();
        try {
            Log.d("fragment manager", fragmentManager.getFragments().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkTabState(int index) {
        if (index == 1) {
            return tabStateManager.ObjectState;
        } else if (index == 2) {
            return tabStateManager.CollectionsState;
        } else if (index == 3) {
            return tabStateManager.SettingsState;
        } else {
            return tabStateManager.MapState;
        }
    }

    private String nowPosition = "";

    public String getPosition() {
        return nowPosition;
    }

    private void hideFragment() {
        if (nowVisibleFragmentTag != null) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(nowVisibleFragmentTag)).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Log.d("onReFM", fragmentManager.toString() + "  " + fragmentManager.getFragments().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String ObjectInfoUrl = "https://us-central1-friendlychat-10420.cloudfunctions.net/sendAllArts";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                finish();
                startActivity(new Intent(HomeActivity.this, ProfileSettingsActivity.class));
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(logDebug, "H.onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(logDebug, "H.onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        Log.d(logDebug, "H.onPause");
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d(logDebug, "H.onStart");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (nowVisibleFragmentTag.equals(ObjectDemoFragment.fragmentTag)) {
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag(ObjectDemoFragment.fragmentTag)).commit();
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(Object.fragmentTag)).commit();
            nowVisibleFragmentTag = Object.fragmentTag;
            titleTextView.setText("創作");
            titleBackimageView.setVisibility(View.INVISIBLE);
        } else if (nowVisibleFragmentTag.contains("collections_")) {
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag(nowVisibleFragmentTag)).commit();
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(Collections.fragmentTag)).commit();
            nowVisibleFragmentTag = Collections.fragmentTag;
            titleTextView.setText("收藏");
            titleBackimageView.setVisibility(View.INVISIBLE);
        }
    }

    public void changeFragment(String fragmentTAG, Fragment fragment) {
        hideFragment();
        if (fragmentTAG.equals(ObjectDemoFragment.fragmentTag)) {
            fragmentManager.beginTransaction().add(R.id.contentContainer, fragment, fragmentTAG).commit();
            nowVisibleFragmentTag = fragmentTAG;
            return;
        }

        if (fragmentManager.findFragmentByTag(fragmentTAG) != null) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(fragmentTAG)).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.contentContainer, fragment, fragmentTAG).commit();
        }
        nowVisibleFragmentTag = fragmentTAG;
    }

    public class TabStateManager {
        public String MapState;
        public String ObjectState;
        public String CollectionsState;
        public String SettingsState;
    }
}
