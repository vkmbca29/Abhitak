package softpro.abhitak.models.video;

public class Item {
    private String kind;
    private String etag;
    private String id;
    private Snippet snippet;

    public Item() {
    }

    public String getKind() {

        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }
}
