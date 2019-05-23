package softpro.abhitak.utils;

import android.content.Context;

/**
 * Created by hp on 16-Jun-18.
 */

public class Utils {
    private static Context context;
    private static Utils single_instance = null;
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Utils.context = context;
    }

    public static Utils getInstance() {
        if (single_instance == null)
            single_instance = new Utils();

        return single_instance;
    }
}
