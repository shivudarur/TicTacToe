package tictactoe.shore.com.tictactoe.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Shiva on 2/17/2016.
 */
public class Utils {
    public static void showToast(Activity activity, int resourceId) {
        Toast.makeText(activity, resourceId, Toast.LENGTH_LONG).show();
    }
}
