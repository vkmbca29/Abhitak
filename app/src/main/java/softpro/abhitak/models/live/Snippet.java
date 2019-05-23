package softpro.abhitak.models.live;

import softpro.abhitak.models.video.ResourceId;
import softpro.abhitak.models.video.Thumbnail;

public class Snippet {
    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    private Thumbnail thumbnails;
    private String channelTitle;
    private String playlistId;
    private int position;

    public Snippet() {
    }

    public String getPublishedAt() {

        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Thumbnail getThumbnails() {
        return thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public int getPosition() {
        return position;
    }
}
