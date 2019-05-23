package softpro.abhitak.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;

import java.util.ArrayList;

import softpro.abhitak.R;
import softpro.abhitak.models.video.Item;
import softpro.abhitak.utils.Helper;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<Item> videos;
    private Context context;
    private YouTubePlayer youTubePlayer;
    private TextView publishedOutlet, titleOutlet;

    public VideoAdapter(Context context, ArrayList<Item> videos) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOutlet(YouTubePlayer youTubePlayer) {
        this.youTubePlayer = youTubePlayer;
    }

    public void setTitleOutlet(TextView titleOutlet) {
        this.titleOutlet = titleOutlet;
    }

    public void setPublishedOutlet(TextView publishedOutlet) {
        this.publishedOutlet = publishedOutlet;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Item item = videos.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (youTubePlayer != null)
                    youTubePlayer.loadVideo(item.getSnippet().getResourceId().getVideoId(), 0);
                if (titleOutlet != null)
                    titleOutlet.setText(item.getSnippet().getTitle());
                if (publishedOutlet != null)
                    publishedOutlet.setText(item.getSnippet().getPublishedAt());
            }
        });
        holder.videoTitle.setText(item.getSnippet().getTitle());
        holder.publishedAt.setText(Helper.getFormattedTimeZone(item.getSnippet().getPublishedAt()));
        Glide.with(context)
                .load(item.getSnippet().getThumbnails().getMedium().getUrl())
                .fitCenter()
                .placeholder(R.drawable.abhi_tak)
                .crossFade()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView videoTitle;
        public TextView publishedAt;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}
