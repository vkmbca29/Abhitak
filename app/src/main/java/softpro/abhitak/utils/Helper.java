package softpro.abhitak.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import softpro.abhitak.models.post.Post;

public class Helper {
    private final static String MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final String URL_POST = "http://www.abhitaknews.in/wp-json/wp/v2/posts/";
    public static final String URL_NEWS = "http://www.abhitaknews.in/wp-json/wp/v2/posts?per_page=20&fields=id,date_gmt,title,excerpt,featured_media";
    public static final String URL_VIDEOS = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=UUQVgzFyRv2RImUisFG0DSYA&key=" + Constants.API_KEY_VIDEO;

    //public static final String URL_VIDEOS_LIVE="https://www.googleapis.com/youtube/v3/search?channelId="+Constants.YOUTUBE_CHANNEL_ID+"&eventType=live&type=video&part=snippet&maxResults=50&key="+Constants.API_KEY_VIDEO;
    public static String getFormattedTime(String time) {
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sd1.parse(time));
        } catch (ParseException e) {
            Log.e("TIME_PARSE_ERROR", e.getMessage());
        }
        return MONTHS[cal.get(Calendar.MONTH)].concat(" ").concat(String.valueOf(cal.get(Calendar.DAY_OF_MONTH))).concat(", ").concat(String.valueOf(cal.get(Calendar.YEAR)));
    }

    public static String getFormattedTimeZone(String time) {
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sd1.parse(time));
        } catch (ParseException e) {
            Log.e("TIME_PARSE_ERROR", e.getMessage());
        }
        return MONTHS[cal.get(Calendar.MONTH)].concat(" ").concat(String.valueOf(cal.get(Calendar.DAY_OF_MONTH))).concat(", ").concat(String.valueOf(cal.get(Calendar.YEAR)));
    }

    public static String getImageUrl(Post post) {
        Element el = Jsoup.parse(post.getContent().getRendered()).body();
        Element img = el.select("img").first();
        String url = null;
        if (img != null) {
            String srcs[] = img.attr("srcset").split(",");
            ArrayList<String> str = new ArrayList<>();
            for (String src : srcs) {
                if (src.trim().length() > 0) {
                    str.add(src.trim());
                }
            }
            if (str.size() > 0) {
                if (str.size() == 1) {
                    url = str.get(0).split(" ")[0];
                }
                if (str.size() > 1) {
                    url = str.get(1).split(" ")[0];
                }
            }
        }
        return url;
    }
}
