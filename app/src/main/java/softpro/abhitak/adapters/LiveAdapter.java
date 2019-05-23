package softpro.abhitak.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;

import java.util.ArrayList;

import softpro.abhitak.R;
import softpro.abhitak.models.live.Item;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {

    private ArrayList<Item> liveVideos;
    private Context context;
    private YouTubePlayer youTubePlayer;
    private TextView publishedOutlet, titleOutlet;

    public LiveAdapter(Context context, ArrayList<Item> liveVideos) {
        this.liveVideos = liveVideos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.live_video_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Item item = liveVideos.get(position);
        holder.videoTitle.setText(item.getSnippet().getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (youTubePlayer != null)
                    youTubePlayer.loadVideo(item.getId().getVideoId(), 0);
                if (titleOutlet != null)
                    titleOutlet.setText(item.getSnippet().getTitle());
                if (publishedOutlet != null)
                    publishedOutlet.setText(item.getSnippet().getPublishedAt());
            }
        });
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
    public int getItemCount() {
        return liveVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView videoTitle;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}
