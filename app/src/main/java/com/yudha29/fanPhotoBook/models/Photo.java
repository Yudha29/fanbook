package com.yudha29.fanPhotoBook.models;

public class Photo {
    int id;
    String artistId;
    String url;

    public Photo(String artistId, String url) {
        this.artistId = artistId;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
