package softpro.abhitak.models.video;

import java.util.ArrayList;

public class Videos {
    private String kind;
    private String nextPageToken;
    private PageInfo pageInfo;
    private String etag;

    public Videos() {
    }

    public String getKind() {

        return kind;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public String getEtag() {
        return etag;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    private ArrayList<Item> items;
}
