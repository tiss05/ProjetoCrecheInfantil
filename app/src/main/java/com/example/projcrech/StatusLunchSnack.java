package com.example.projcrech;

import java.io.Serializable;

public class StatusLunchSnack implements Serializable {

    private String name;
    private int image;

    public StatusLunchSnack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
