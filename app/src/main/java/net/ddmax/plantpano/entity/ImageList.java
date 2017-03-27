package net.ddmax.plantpano.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author ddMax
 * @since 2017-03-27 02:44 PM.
 */

public class ImageList implements Parcelable {
    @SerializedName("_items")
    private List<Image> items;

    protected ImageList(Parcel in) {
        in.readList(this.items, Image.class.getClassLoader());
    }

    public static final Creator<ImageList> CREATOR = new Creator<ImageList>() {
        @Override
        public ImageList createFromParcel(Parcel in) {
            return new ImageList(in);
        }

        @Override
        public ImageList[] newArray(int size) {
            return new ImageList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.items);
    }

    public List<Image> getItems() {
        return items;
    }

    public void setItems(List<Image> items) {
        this.items = items;
    }
}
