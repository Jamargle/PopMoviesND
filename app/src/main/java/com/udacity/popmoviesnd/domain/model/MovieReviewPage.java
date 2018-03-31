package com.udacity.popmoviesnd.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class MovieReviewPage {

    @SerializedName("page") private int page;
    @SerializedName("results") private List<MovieReview> reviews;
    @SerializedName("total_pages") private int totalPages;
    @SerializedName("total_results") private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public List<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(final List<MovieReview> reviews) {
        this.reviews = reviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

}
