package com.example.nasa;

public class Nasa {

    int id;
    private String title;
    private byte[] image;

    public Nasa() {}

    public byte[] getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
