package softpro.abhitak.models.live;

public class Item {
    private String kind;
    private String etag;
    private Id id;
    private Snippet snippet;

    public Item() {
    }

    public String getKind() {

        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public Id getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

}
