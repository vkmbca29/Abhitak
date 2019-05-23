package softpro.abhitak.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

import naseem.ali.flexibletoast.EasyToast;
import softpro.abhitak.MainActivity;
import softpro.abhitak.R;
import softpro.abhitak.Utils;
import softpro.abhitak.adapters.EndlessRecyclerOnScrollListener;
import softpro.abhitak.adapters.JanpadAdapter;
import softpro.abhitak.adapters.NewsAdapter;
import softpro.abhitak.models.JanpadModel;
import softpro.abhitak.models.post.partial.Post;
import softpro.abhitak.utils.Constants;
import softpro.abhitak.utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    private NewsAdapter newsAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    private ProgressBar loader;
    private FloatingActionButton fab;
    private Spinner janpadSpinner;
    private int selected = 5;
    private int page = 1;
    private static boolean LOADED = false;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        RecyclerView newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsAdapter = new NewsAdapter(getContext(), posts);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        newsRecyclerView.setLayoutManager(mLayoutManager);
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMore();
                //EasyToast.makeText(getContext(),"Loading..",EasyToast.LENGTH_SHORT,EasyToast.INFO).show();
            }
        });
        loader = view.findViewById(R.id.loader);
        fab = view.findViewById(R.id.fab);
        janpadSpinner = view.findViewById(R.id.janpadSpinner);
        ArrayList<JanpadModel> janpadModels = new ArrayList<>();
        for (String janpad : getResources().getStringArray(R.array.janpads)) {
            janpadModels.add(new JanpadModel(janpad));
        }
        JanpadAdapter adapter = new JanpadAdapter(getContext(), janpadModels);
        janpadSpinner.setAdapter(adapter);
        janpadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = i;
                loadNews(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        janpadSpinner.setSelection(5);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNews(selected);
            }
        });
        if (Utils.getInstance().isNetworkAvailable(getContext())) {
            if (!LOADED) {
                loadNews(selected);
            }
        } else {
            EasyToast.makeText(Utils.getInstance().getContext(), "No internet connection", EasyToast.LENGTH_SHORT, EasyToast.ERROR).show();
        }
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            MainActivity activity = ((MainActivity) Utils.getInstance().getContext());
            activity.setPortrait();
        }
    }

    void loadNews(int i) {
        loader.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String URL = Helper.URL_NEWS;
        page = 1;
        switch (i) {
            case 0:
                URL += "&categories=" + Constants.CATEGORY_BIJNOR;
                break;
            case 1:
                URL += "&categories=" + Constants.CATEGORY_DHAMPUR;
                break;
            case 2:
                URL += "&categories=" + Constants.CATEGORY_NAJIBABAD;
                break;
            case 3:
                URL += "&categories=" + Constants.CATEGORY_NAGINA;
                break;
            case 4:
                URL += "&categories=" + Constants.CATEGORY_CHANDPUR;
                break;
        }
        URL += "&page=" + page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LOADED = true;
                        loader.setVisibility(View.GONE);
                        Gson gsonObj = new Gson();
                        Post[] postsArray = gsonObj.fromJson(response, Post[].class);
                        if (postsArray.length > 0) {
                            posts.clear();
                            for (Post post : postsArray) {
                                posts.add(post);
                            }
                            newsAdapter.notifyDataSetChanged();
                        } else {
                            EasyToast.makeText(getContext(), "Sorry No Posts Found", EasyToast.LENGTH_SHORT, EasyToast.ERROR).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
                EasyToast.makeText(getContext(), "Some error occured", EasyToast.LENGTH_SHORT, EasyToast.ERROR).show();
            }
        });
        queue.add(stringRequest);
    }

    private void loadMore() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String URL = Helper.URL_NEWS;
        switch (selected) {
            case 0:
                URL += "&categories=" + Constants.CATEGORY_BIJNOR;
                break;
            case 1:
                URL += "&categories=" + Constants.CATEGORY_DHAMPUR;
                break;
            case 2:
                URL += "&categories=" + Constants.CATEGORY_NAJIBABAD;
                break;
            case 3:
                URL += "&categories=" + Constants.CATEGORY_NAGINA;
                break;
            case 4:
                URL += "&categories=" + Constants.CATEGORY_CHANDPUR;
                break;
        }
        page++;
        URL += "&page=" + page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.setVisibility(View.GONE);
                        Gson gsonObj = new Gson();
                        Post[] postsArray = gsonObj.fromJson(response, Post[].class);
                        if (postsArray.length > 0) {
                            for (Post post : postsArray) {
                                posts.add(post);
                            }
                            newsAdapter.notifyDataSetChanged();
                        } else {
                            EasyToast.makeText(getContext(), "Sorry No Posts Found", EasyToast.LENGTH_SHORT, EasyToast.ERROR).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.setVisibility(View.GONE);
                //EasyToast.makeText(getContext(),"Some error occured",EasyToast.LENGTH_SHORT,EasyToast.ERROR).show();
            }
        });
        queue.add(stringRequest);
    }

}
