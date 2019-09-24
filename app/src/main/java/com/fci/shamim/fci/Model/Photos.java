package com.fci.shamim.fci.Model;

public class Photos {

    String photoId;
    String photo;
    String description;

    public Photos(String photoId, String photo, String description) {
        this.photoId = photoId;
        this.photo = photo;
        this.description = description;
    }

    public Photos() {
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
