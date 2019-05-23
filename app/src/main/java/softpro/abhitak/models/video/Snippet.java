package softpro.abhitak.models.video;

public class Snippet {
    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    private Thumbnail thumbnails;
    private String channelTitle;
    private String playlistId;
    private int position;
    private ResourceId resourceId;

    public ResourceId getResourceId() {
        return resourceId;
    }

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
