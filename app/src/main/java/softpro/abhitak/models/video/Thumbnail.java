package softpro.abhitak.models.video;

public class Thumbnail {
    private ThumbModel defaulT;
    private ThumbModel medium;
    private ThumbModel high;
    private ThumbModel standard;

    public Thumbnail() {
    }

    public ThumbModel getDefault() {
        return defaulT;
    }

    public ThumbModel getMedium() {
        return medium;
    }

    public ThumbModel getHigh() {
        return high;
    }

    public ThumbModel getStandard() {
        return standard;
    }
}
