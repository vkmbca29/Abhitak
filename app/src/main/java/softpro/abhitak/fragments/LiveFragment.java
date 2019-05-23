package softpro.abhitak.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import softpro.abhitak.MainActivity;
import softpro.abhitak.R;
import softpro.abhitak.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveFragment extends Fragment {

    private WebView webView;
    private boolean PLAYER_READY = false;
    private boolean LIVE = false;
    private PlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    private final String videoURL = "http://51.254.38.220/abhitakhls/abhitak.m3u8";
    private ImageButton fullScreen;
    private boolean VISIBLE = false;
    private boolean IS_FULLSCREEN = false;

    public LiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LiveFragment.
     */
    public static LiveFragment newInstance() {
        LiveFragment fragment = new LiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        fullScreen = view.findViewById(R.id.fullScreen);
        exoPlayerView = view.findViewById(R.id.video_view);
        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            Uri videoURI = Uri.parse(videoURL);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .setPlaylistParser(
                            new FilteringManifestParser<>(
                                    new HlsPlaylistParser(), /*(List<RenditionKey>) getOfflineStreamKeys(videoURI)*/null))
                    .createMediaSource(videoURI);
            exoPlayer.prepare(mediaSource);
            exoPlayerView.setPlayer(exoPlayer);
        } catch (Exception e) {
            Log.e("MainActivity", " exoplayer error " + e.toString());
        }
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = ((MainActivity) Utils.getInstance().getContext());
                if (IS_FULLSCREEN) {
                    activity.setPortrait();
                    fullScreen.setImageResource(R.drawable.ic_fullscreen_black_24dp);
                } else {
                    activity.setLandscape();
                    fullScreen.setImageResource(R.drawable.ic_fullscreen_exit_black_24dp);
                }
                activity.collapse();
                IS_FULLSCREEN = !IS_FULLSCREEN;
            }
        });
        return view;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if (exoPlayer != null) {
                exoPlayerView.setPlayer(exoPlayer);
                exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                exoPlayer.seekTo(0);
                exoPlayer.setPlayWhenReady(true);
            }
            MainActivity activity = (MainActivity) Utils.getInstance().getContext();
            activity.collapse();
        } else {
            if (exoPlayer != null) {
                exoPlayer.seekTo(0);
                exoPlayer.setPlayWhenReady(false);
                exoPlayerView.setPlayer(null);
            }
        }
        VISIBLE = visible;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayer.seekTo(0);
            exoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (VISIBLE) {
            if (exoPlayer != null) {
                exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                exoPlayer.seekTo(0);
                exoPlayer.setPlayWhenReady(true);
            }
            MainActivity activity = (MainActivity) Utils.getInstance().getContext();
            activity.collapse();
            if (IS_FULLSCREEN) {
                fullScreen.setImageResource(R.drawable.ic_fullscreen_exit_black_24dp);
            } else {
                fullScreen.setImageResource(R.drawable.ic_fullscreen_black_24dp);
            }
        }
    }
}

