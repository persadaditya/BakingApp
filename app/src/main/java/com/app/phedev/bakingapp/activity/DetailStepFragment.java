package com.app.phedev.bakingapp.activity;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.phedev.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetailStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailStepFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_DESC = "description";
    public static final String ARG_VIDEO = "video";
    public static final String ARG_THUMBNAIL = "thumbnail";
    public static final String ARG_POS = "pos";
    public static final String ARG_SHORT_DESC = "short_desc";
    public static final String ARG_ITEM_ID = "item_id";
    private static final String PLAYWHENREADY = "ready_to_play";
    private static final String PLAYBACKPOSITION = "play_back";
    private static final String CURRENTWINDOW = "window_current";


    private String mDesc;
    private String mVideo;
    private String mThumb;
    private int mPos;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private int currentWindow;
    private long playbackPosition;
    private boolean playWhenReady = true;

    public DetailStepFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailStepFragment.
     */
    public static DetailStepFragment newInstance(String param1, String param2, String param3, int mPos) {
        DetailStepFragment fragment = new DetailStepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESC, param1);
        args.putString(ARG_VIDEO, param2);
        args.putString(ARG_THUMBNAIL, param3);
        args.putInt(ARG_ITEM_ID, mPos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDesc = getArguments().getString(ARG_DESC);
            mVideo = getArguments().getString(ARG_VIDEO);
            mThumb = getArguments().getString(ARG_THUMBNAIL);
            mPos = getArguments().getInt(ARG_POS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAYWHENREADY, playWhenReady);
        outState.putInt(CURRENTWINDOW, currentWindow);
        outState.putLong(PLAYBACKPOSITION, playbackPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(PLAYWHENREADY);
            currentWindow = savedInstanceState.getInt(CURRENTWINDOW);
            playbackPosition = savedInstanceState.getLong(PLAYBACKPOSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.recipe_step_detail_text);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.step_detail_video);
        if (mVideo == null) {
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeFile(mThumb));
        }
        textView.setText(mDesc);
        return rootView;
    }

    private void initializePlayer() {
        if (mVideo == null) return;
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity())
                , new DefaultTrackSelector(), new DefaultLoadControl());
        mPlayerView.setPlayer(mExoPlayer);
        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(mVideo);
        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri
                , new DefaultHttpDataSourceFactory("ua")
                , new DefaultExtractorsFactory(), null, null);
    }

    private void releasePlayer(){
        if (mExoPlayer != null){
            playWhenReady = mExoPlayer.getPlayWhenReady();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playbackPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void hideSystemUI() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        hideSystemUI();
        if (Util.SDK_INT > 23){
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUI();
        if (Util.SDK_INT <= 23 || mExoPlayer == null){
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23){
            releasePlayer();
        }
    }
}

