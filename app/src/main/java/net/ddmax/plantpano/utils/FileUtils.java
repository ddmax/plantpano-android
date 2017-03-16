package net.ddmax.plantpano.utils;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * @author ddMax
 * @since 2017-03-07 12:11 AM.
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    // private constructor to enforce Singleton pattern
    private FileUtils() {}

    /**
     * Convert Image Uri into File.
     */
    public static File getImageFile(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return new File(s);
    }

}

