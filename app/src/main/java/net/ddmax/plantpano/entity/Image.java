package net.ddmax.plantpano.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddMax
 * @since 2017-03-26 05:02 PM.
 * 说明：单个图像实体类
 */
public class Image implements Parcelable {
    private String name;
    @SerializedName("image")
    private String imageLink;
    @SerializedName("is_pub")
    private boolean isPub;
    @SerializedName("user")
    private String userId;
    private List<Result> result;
    private int review;
    private int likeit;
    private List<Comment> comments;

    public static class Result implements Parcelable{
        private String name;
        private String score;

        protected Result(Parcel in) {
            name = in.readString();
            score = in.readString();
        }

        public static final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(score);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

    protected Image(Parcel in) {
        this.name = in.readString();
        this.imageLink = in.readString();
        this.isPub = in.readByte() != 0;
        this.userId = in.readString();
        this.result = new ArrayList<>();
        in.readList(this.result, Result.class.getClassLoader());
        this.review = in.readInt();
        this.likeit = in.readInt();
        this.comments = new ArrayList<>();
        in.readList(this.comments, Comment.class.getClassLoader());
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.imageLink);
        dest.writeByte((byte) (isPub ? 1 : 0));
        dest.writeString(this.userId);
        dest.writeList(this.result);
        dest.writeInt(this.review);
        dest.writeInt(this.likeit);
        dest.writeList(this.comments);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink == null ? "" : imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public boolean isPub() {
        return isPub;
    }

    public void setPub(boolean pub) {
        isPub = pub;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getLikeit() {
        return likeit;
    }

    public void setLikeit(int likeit) {
        this.likeit = likeit;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
