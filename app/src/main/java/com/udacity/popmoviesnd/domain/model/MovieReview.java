package com.udacity.popmoviesnd.domain.model;

import com.google.gson.annotations.SerializedName;

public final class MovieReview {

    @SerializedName("id") private String id;
    @SerializedName("author") private String author;
    @SerializedName("content") private String content;
    @SerializedName("url") private String url;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

}
