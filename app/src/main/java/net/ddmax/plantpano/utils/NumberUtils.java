package net.ddmax.plantpano.utils;

import android.util.Log;

/**
 * @author ddMax
 * @since 2017-03-15 12:43 PM.
 */

public class NumberUtils {
    public static final String TAG = NumberUtils.class.getSimpleName();

    public static String convertToPercent(double num) {
        if (num <= 0)
            return "0%";
        String percentStr = String.format("%.2f", num * 100) + "%";
        return percentStr;
    }

    public static String convertToPercent(String num) {
        try {
            double n = Double.parseDouble(num);
            return convertToPercent(n);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }
}
