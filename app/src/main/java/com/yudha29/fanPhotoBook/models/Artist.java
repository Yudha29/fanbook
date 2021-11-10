package com.yudha29.fanPhotoBook.models;

public class Artist {
    int id;
    String name;
    String profileImage;
    String debutDate;
    String description;

    public Artist(String name, String profileImage, String debutDate, String description) {
        this.name = name;
        this.profileImage = profileImage;
        this.debutDate = debutDate;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDebutDate() {
        return debutDate;
    }

    public void setDebutDate(String debutDate) {
        this.debutDate = debutDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
