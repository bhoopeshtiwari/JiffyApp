package com.jiffy.jiffyapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiffy.jiffyapp.R;
import com.jiffy.jiffyapp.constant.ApplicationConstant;
import com.jiffy.jiffyapp.manager.MySingleton;

/**
 * Created by Bhoopesh.
 */
public class VideoSelectionFragment extends BaseFragment {

    public static final String TAG = "VideoSelectionFragment";
    private Context mContext;
    private View view;
    private RadioButton rbYouTube;
    private RadioButton rbM3U8;

    public VideoSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_video_selection, container, false);
        findViews();
        return view;
    }

    /***
     * Method to find views and listeners
     ***/
    private void findViews() {
        rbYouTube = (RadioButton) view.findViewById(R.id.rbYouTube);
        rbM3U8 = (RadioButton) view.findViewById(R.id.rbM3U8);
        if (MySingleton.getInstance().isYoutubeSelected()) {
            rbYouTube.setChecked(true);
        } else {
            rbM3U8.setChecked(true);
        }
        rbYouTube.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    MySingleton.getInstance().setYoutubeSelected(true);
                    ApplicationConstant.log(TAG, "YOUTUBE SELECTED");
                } else {
                    ApplicationConstant.log(TAG, "M3U8 SELECTED");
                    MySingleton.getInstance().setYoutubeSelected(false);
                }
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

}
