package net.ddmax.plantpano.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ddMax
 * @since 2017-03-27 03:44 PM.
 */

public class Comment implements Parcelable {
    private String content;
    private String user;

    protected Comment(Parcel in) {
        content = in.readString();
        user = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.user);
    }
}
