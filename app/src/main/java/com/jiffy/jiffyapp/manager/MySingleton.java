package com.jiffy.jiffyapp.manager;

import com.jiffy.jiffyapp.interfaces.OnPauseCalled;
import com.jiffy.jiffyapp.model.ContactsBean;

import java.util.ArrayList;

/**
 * Created by Bhoopesh.
 */
public class MySingleton {
    private static MySingleton ourInstance = new MySingleton();
    private boolean isYoutubeSelected = true;
    private ArrayList<ContactsBean> mContactsList = new ArrayList<>();
    private OnPauseCalled onPauseCalled;

    public static MySingleton getInstance() {
        return ourInstance;
    }

    private MySingleton() {
    }

    public boolean isYoutubeSelected() {

        return isYoutubeSelected;
    }

    public void setYoutubeSelected(boolean youtubeSelected) {
        isYoutubeSelected = youtubeSelected;
    }

    public ArrayList<ContactsBean> getmContactsList() {
        return mContactsList;
    }

    public void setmContactsList(ArrayList<ContactsBean> mContactsList) {
        this.mContactsList = mContactsList;
    }

    public OnPauseCalled getOnPauseCalled() {
        return onPauseCalled;
    }

    public void setOnPauseCalled(OnPauseCalled onPauseCalled) {
        this.onPauseCalled = onPauseCalled;
    }
}
