package com.jiffy.jiffyapp.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.jiffy.jiffyapp.R;
import com.jiffy.jiffyapp.constant.ApplicationConstant;
import com.jiffy.jiffyapp.fragment.ContactsFragment;
import com.jiffy.jiffyapp.fragment.VideoPlayerFragment;
import com.jiffy.jiffyapp.fragment.VideoSelectionFragment;
import com.jiffy.jiffyapp.fragment.YoutubeFragment;
import com.jiffy.jiffyapp.manager.MySingleton;


/**
 * Created by Bhoopesh.
 */
public class HomeActivity extends AppCompatActivity {

    private TabWidget mTabWidget;
    private FragmentTabHost mTabHost;
    private String TAG = "HomeActivity";
    private String TAB_1_TAG = "Player";
    private String TAB_2_TAG = "Preference";
    private String TAB_3_TAG = "Contacts";
    private FrameLayout tabcontent;
    private boolean doubleBackToExitPressedOnce = false;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findIds();
    }


    /***
     * Method  to find views and prepare tabs
     ****/
    private void findIds() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(getTabIndicator(TAB_1_TAG)),
                YoutubeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(getTabIndicator(TAB_2_TAG)),
                VideoSelectionFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_3_TAG).setIndicator(getTabIndicator(TAB_3_TAG)),
                ContactsFragment.class, null);

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                ApplicationConstant.log(TAG, "onTabChanged--" + s);
                mTabHost.getTabContentView().removeAllViews();
                if (TAB_1_TAG.equals(s)) {
                    if (MySingleton.getInstance().isYoutubeSelected()) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(mTabHost.getTabContentView().getId(), new YoutubeFragment()).commit();
                    } else {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(mTabHost.getTabContentView().getId(), new VideoPlayerFragment()).commit();
                    }
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /***
     * Method to get tab indicator for tabs
     ****/
    private View getTabIndicator(String title) {
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.custom_tab_item, null);
        TextView tabTextView = (TextView) view.findViewById(R.id.text_tab_name);
        tabTextView.setText(title);
        tabTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return true;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                return false;
            }
        });
        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (MySingleton.getInstance().getOnPauseCalled() != null) {
            MySingleton.getInstance().getOnPauseCalled().stopVideo();
        }
        ApplicationConstant.log(TAG, "OPause() invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationConstant.log(TAG, "onDestroy() invoked");
    }


    @Override
    protected void onResume() {
        super.onResume();
        ApplicationConstant.log(TAG, "Resume() invoked");
    }

    @Override
    public void onBackPressed() {
        if (this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        ApplicationConstant.showSnckbar(mTabHost, "Press back again to exit app");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ApplicationConstant.log(TAG, "Stop() invoked");
    }

}
