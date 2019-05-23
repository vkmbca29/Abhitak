package softpro.abhitak;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    private static final Utils ourInstance = new Utils();

    private Context context;
    private String pageData;

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void readHlsPlayerHTMLFromFile(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.naseem);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String read;
            StringBuilder sb = new StringBuilder();

            while ((read = bufferedReader.readLine()) != null)
                sb.append(read).append("\n");
            inputStream.close();

            pageData = sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Can't parse HTML file containing the player.");
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPageData() {
        return this.pageData;
    }

    public Context getContext() {
        return context;
    }
}
