
package softpro.abhitak.models.post.partial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("excerpt")
    @Expose
    private Excerpt excerpt;
    @SerializedName("featured_media")
    @Expose
    private Long featuredMedia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Excerpt getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    public Long getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(Long featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

}
