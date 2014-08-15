package nerd_is.in.number_converter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

/**
 * Created by Zheng Xuqiang on 2014/8/15 0015.
 */
public class PreferenceUtils {

    public static final String PREF_NAME_APP_COMMON = "app_common_pref";
    public static final String PREF_VALUE_POSITION_X = "position_x";
    public static final String PREF_VALUE_POSITION_Y = "position_y";
    public static final String PREF_VALUE_FIRST_FLOATING = "floating_first_time";

    public static SharedPreferences getCommonPref(Context context) {
        return context.getSharedPreferences(PREF_NAME_APP_COMMON, Context.MODE_PRIVATE);
    }

    public static Point loadFloatingViewPosition(Context context) {
        SharedPreferences sp = getCommonPref(context);
        return new Point(
                sp.getInt(PREF_VALUE_POSITION_X, -1),
                sp.getInt(PREF_VALUE_POSITION_Y, -1)
        );
    }

    public static void saveFloatingViewPosition(Context context, int x, int y) {
        SharedPreferences sp = getCommonPref(context);
        sp.edit()
                .putInt(PREF_VALUE_POSITION_X, x)
                .putInt(PREF_VALUE_POSITION_Y, y)
                .apply();
    }

    public static boolean isFirstTimeFloating(Context context) {
        return getCommonPref(context).getBoolean(PREF_VALUE_FIRST_FLOATING, true);
    }

}
