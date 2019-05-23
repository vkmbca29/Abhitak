package softpro.abhitak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import naseem.ali.flexibletoast.EasyToast;
import softpro.abhitak.models.post.Post;
import softpro.abhitak.utils.Helper;

public class ScrollingActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title;
    private TextView sourceName;
    private TextView sourceURL;
    private TextView description;
    private TextView publishedAt;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = findViewById(R.id.expandedImage);
        title = findViewById(R.id.title);
        sourceName = findViewById(R.id.sourceName);
        sourceURL = findViewById(R.id.sourceURL);
        description = findViewById(R.id.description);
        publishedAt = findViewById(R.id.publishedAt);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "News Added To Bookmarks", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        String articleTitle = bundle.getString("TITLE");
        String excerpt = bundle.getString("DESC");
        String time = bundle.getString("TIME");
        publishedAt.setText(Helper.getFormattedTime(time));
        setText(description, excerpt);
        long postId = bundle.getLong("ID");
        title.setText(articleTitle);
        loadPost(postId);
        setTitle("Abhitak");
    }

    private void showLink(TextView link, String url) {
        String linkText = "Visit the <a href='" + url + "'> Source </a>";
        link.setText(Html.fromHtml(linkText));
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setText(TextView textView, String text) {
        textView.setText(Html.fromHtml(text));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void loadImage(String image) {
        if (image != null) {
            if (image.trim().length() != 0) {
                Glide.with(this)
                        .load(image)
                        .fitCenter()
                        .placeholder(R.drawable.about_icon_link)
                        .crossFade()
                        .into(imageView);
            }
        }
    }

    private void loadPost(long id) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Helper.URL_POST + id + "?fields=id,date_gmt,guid,linktitle,content,excerpt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gsonObj = new Gson();
                        Post post = gsonObj.fromJson(response, Post.class);
                        setText(description, post.getContent().getRendered().replaceAll("\\<.*?>", ""));
                        showLink(sourceURL, post.getGuid().getRendered());
                        loadImage(Helper.getImageUrl(post));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                EasyToast.makeText(ScrollingActivity.this, "Some error occured", EasyToast.LENGTH_SHORT, EasyToast.ERROR).show();
            }
        });
        queue.add(stringRequest);
    }

}
