package com.example.mferraco.popularmovies.responseModels;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * This object represents a trailer json object in the response from TheMovieDB API.
 */
public class Trailer implements Parcelable {

    private static final String TAG = Trailer.class.getSimpleName();

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public Trailer() {
        // No-op
    }

    public static Trailer fromJson(JSONObject trailerDataJson) {
        Trailer trailer = new Trailer();

        trailer.setId(trailerDataJson.optString("id"));
        trailer.setIso_639_1(trailerDataJson.optString("iso_639_1"));
        trailer.setIso_3166_1(trailerDataJson.optString("iso_3166_1"));
        trailer.setKey(trailerDataJson.optString("key"));
        trailer.setName(trailerDataJson.optString("name"));
        trailer.setSite(trailerDataJson.optString("site"));
        trailer.setSize(trailerDataJson.optInt("size"));
        trailer.setType(trailerDataJson.optString("type"));

        return trailer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /* Parcelable Interface */

    public Trailer(Parcel in) {
        this.id = in.readString();
        this.iso_639_1 = in.readString();
        this.iso_3166_1 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iso_639_1);
        dest.writeString(iso_3166_1);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(type);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {

        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
