package com.jiffy.jiffyapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.jiffy.jiffyapp.R;
import com.jiffy.jiffyapp.constant.ApplicationConstant;
import com.jiffy.jiffyapp.interfaces.OnPauseCalled;
import com.jiffy.jiffyapp.manager.MySingleton;

/**
 * Created by Bhoopesh.
 */
public class VideoPlayerFragment extends BaseFragment implements OnPreparedListener, OnPauseCalled {
    public static final String TAG = "VideoPlayerFragment";
    private Context mContext;
    private View view;
    private EMVideoView emVideoView;

    public VideoPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_video_player, container, false);
        findViews();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /**Method to find views and get ready to play video**/
    private void findViews() {
        MySingleton.getInstance().setOnPauseCalled(this);
        emVideoView = (EMVideoView) view.findViewById(R.id.emVideoView);
        emVideoView.setOnPreparedListener(this);
        if (ApplicationConstant.isOnline(mContext)) {
            emVideoView.setVideoURI(Uri.parse(ApplicationConstant.M3U8_URL));
        }else{
            ApplicationConstant.showSnckbar(view,getString(R.string.network_error));
        }
    }

    @Override
    public void onPrepared() {
        //Starts the video playback as soon as it is ready
        emVideoView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (emVideoView != null) {
            emVideoView.stopPlayback();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (emVideoView != null) {
            emVideoView.stopPlayback();
        }
        super.onDestroy();
    }

    @Override
    public void stopVideo() {
        if (emVideoView != null) {
            emVideoView.stopPlayback();
        }
    }
}
