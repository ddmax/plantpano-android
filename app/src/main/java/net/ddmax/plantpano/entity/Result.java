package net.ddmax.plantpano.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddMax
 * @since 2017-03-12 04:37 PM.
 */

public class Result implements Parcelable {
    private int code;
    private String message;
    private List<ScoreBean> result;

    public static class ScoreBean implements Parcelable {
        private String name;
        private String score;

        protected ScoreBean(Parcel in) {
            name = in.readString();
            score = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(score);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ScoreBean> CREATOR = new Creator<ScoreBean>() {
            @Override
            public ScoreBean createFromParcel(Parcel in) {
                return new ScoreBean(in);
            }

            @Override
            public ScoreBean[] newArray(int size) {
                return new ScoreBean[size];
            }
        };

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

    public Result() {
    }

    protected Result(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.result = new ArrayList<>();
        in.readList(this.result, ScoreBean.class.getClassLoader());
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
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeList(this.result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ScoreBean> getResult() {
        return result;
    }

    public void setResult(List<ScoreBean> result) {
        this.result = result;
    }
}
