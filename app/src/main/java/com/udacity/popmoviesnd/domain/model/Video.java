package com.udacity.popmoviesnd.domain.model;

import com.google.gson.annotations.SerializedName;

public final class Video {

    @SerializedName("key") private String urlKey;
    @SerializedName("name") private String name;
    @SerializedName("site") private String site;
    @SerializedName("size") private int size;
    @SerializedName("type") private String type;

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(final String key) {
        this.urlKey = key;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(final String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
