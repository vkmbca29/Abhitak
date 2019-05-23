package softpro.abhitak.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.util.ArrayList;

import naseem.ali.flexibletoast.EasyToast;
import softpro.abhitak.MainActivity;
import softpro.abhitak.R;
import softpro.abhitak.Utils;
import softpro.abhitak.adapters.VideoAdapter;
import softpro.abhitak.models.video.Item;
import softpro.abhitak.models.video.Videos;
import softpro.abhitak.utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideosFragment extends Fragment {

    private ArrayList<Item> videos = new ArrayList<>();
    private VideoAdapter videoAdapter;
    private ProgressBar loader;
    private static boolean LOADED = false;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    private RecyclerView videoRecyclerView;
    private TextView videoTitle, publishedAt;
    private View playerContainer;

    public VideosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VideosFragment.
     */
    public static VideosFragment newInstance() {
        VideosFragment fragment = new VideosFragment();
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
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        videoRecyclerView = view.findViewById(R.id.videosRecyclerView);
        videoAdapter = new VideoAdapter(getContext(), videos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        videoRecyclerView.setHasFixedSize(true);
        videoRecyclerView.setLayoutManager(mLayoutManager);
        videoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        videoRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        videoRecyclerView.setAdapter(videoAdapter);
        loader = view.findViewById(R.id.loader);
        videoTitle = view.findViewById(R.id.videoTitle);
        videoAdapter.setTitleOutlet(videoTitle);
        videoAdapter.setPublishedOutlet(publishedAt);
        publishedAt = view.findViewById(R.id.publishedAt);
        playerContainer = view.findViewById(R.id.playerContainer);
        final FloatingActionButton fab = view.findViewById(R.id.fab);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                MainActivity activity = ((MainActivity) Utils.getInstance().getContext());
                activity.setLandscape();
                fab.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                MainActivity activity = ((MainActivity) Utils.getInstance().getContext());
                activity.setPortrait();
                activity.collapse();
                fab.setVisibility(View.VISIBLE);
            }
        });
        getLifecycle().addObserver(youTubePlayerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadVideos();
            }
        });
        return view;
    }

    private void setupPlayer() {
        videoTitle.setText(videos.get(0).getSnippet().getTitle());
        publishedAt.setText(Helper.getFormattedTimeZone(videos.get(0).getSnippet().getPublishedAt()));
        youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(final YouTubePlayer initializedYouTubePlayer) {
                youTubePlayer = initializedYouTubePlayer;
                videoAdapter.setOutlet(youTubePlayer);
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String videoId = videos.get(0).getSnippet().getResourceId().getVideoId();
                        initializedYouTubePlayer.cueVideo(videoId, 0);
                    }

                    @Override
                    public void onError(int error) {
                        EasyToast.makeText(getContext(), "Unable to play the video", EasyToast.LENGTH_SHORT, EasyToast.ERROR);
                    }
                });
            }
        }, true);
        youTubePlayerView.getPlayerUIController().showFullscreenButton(true);
    }

    private void loadVideos() {
        loader.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Helper.URL_VIDEOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LOADED = true;
                        loader.setVisibility(View.GONE);
                        Gson gsonObj = new Gson();
                        Videos vids = gsonObj.fromJson(response, Videos.class);
                        videos.clear();
                        videos.addAll(vids.getItems());
                        if (vids.getItems().size() > 0) {
                            setupPlayer();
                            playerContainer.setVisibility(View.VISIBLE);
                        } else {
                            playerContainer.setVisibility(View.INVISIBLE);
                        }
                        videoAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
                EasyToast.makeText(getContext(), error.getMessage(), EasyToast.LENGTH_SHORT, EasyToast.ERROR).show();
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        //Log.e("VideosVisibility",String.valueOf(visible));
        if (videoAdapter != null)
            if (!visible && youTubePlayer != null) {
                youTubePlayer.pause();
            }
        if (visible) {
            MainActivity activity = ((MainActivity) Utils.getInstance().getContext());
            activity.setPortrait();
            if (Utils.getInstance().isNetworkAvailable(Utils.getInstance().getContext())) {
                if (!LOADED) {
                    loadVideos();
                }
            } else {
                EasyToast.makeText(getContext(), "No internet connection", EasyToast.LENGTH_SHORT, EasyToast.ERROR).show();
            }
        } else {
            if (youTubePlayerView != null) {
                youTubePlayerView.exitFullScreen();
            }
        }
    }

}
