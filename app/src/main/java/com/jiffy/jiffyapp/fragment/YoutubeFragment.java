package com.jiffy.jiffyapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.jiffy.jiffyapp.R;
import com.jiffy.jiffyapp.constant.ApplicationConstant;

/**
 * Created by Bhoopesh.
 */
public class YoutubeFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    public static final String TAG = "YoutubeFragment";
    private Context mContext;
    private View view;

    public YoutubeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_youtube, container, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).addToBackStack(null).commit();
        youTubePlayerFragment.initialize(ApplicationConstant.YOUTUBE_DEVELOPER_KEY, this);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /**
     * Youtube callback
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.setFullscreenControlFlags(0);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youTubePlayer.loadVideo(ApplicationConstant.YOUTUBE_VIDEO_CODE);
            youTubePlayer.play();
        }
    }

    /**
     * Youtube callback.
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String errorMessage = youTubeInitializationResult.toString();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        Log.d("errorMessage:", errorMessage);
    }
}
