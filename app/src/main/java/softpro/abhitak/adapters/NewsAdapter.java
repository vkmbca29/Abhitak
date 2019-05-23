package softpro.abhitak.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;
import softpro.abhitak.R;
import softpro.abhitak.ScrollingActivity;
import softpro.abhitak.models.post.partial.Post;
import softpro.abhitak.utils.Helper;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context context;

    public NewsAdapter(Context context, ArrayList<Post> posts) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = posts.get(position);
        holder.article.setText(post.getTitle().getRendered().trim());
        setText(holder.article, post.getTitle().getRendered().trim());
        setText(holder.publishedAt, Helper.getFormattedTime(post.getDateGmt()));
        setText(holder.description, post.getExcerpt().getRendered().trim());
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("TITLE", post.getTitle().getRendered());
                bundle.putLong("ID", post.getId());
                bundle.putString("DESC", post.getExcerpt().getRendered());
                bundle.putString("TIME", post.getDateGmt());
                readMore(bundle);
            }
        });
    }

    public void setText(TextView textView, String text) {
        textView.setText(Html.fromHtml(text));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void readMore(Bundle bundle) {
        Intent intent = new Intent(context, ScrollingActivity.class);
        intent.putExtra("BUNDLE", bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView article;
        public TextView description;
        public TextView publishedAt;
        public FancyButton readMore;

        public ViewHolder(View itemView) {
            super(itemView);
            article = itemView.findViewById(R.id.article);
            description = itemView.findViewById(R.id.description);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            readMore = itemView.findViewById(R.id.readMore);
        }
    }
}
