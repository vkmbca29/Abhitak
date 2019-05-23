package softpro.abhitak.models.live;

import java.util.ArrayList;
import softpro.abhitak.models.video.PageInfo;

public class Videos {
    private String kind;
    private String etag;
    private String regionCode;
    private PageInfo pageInfo;
    private ArrayList<Item> items;

    public Videos() {
    }

    public String getKind() {

        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
