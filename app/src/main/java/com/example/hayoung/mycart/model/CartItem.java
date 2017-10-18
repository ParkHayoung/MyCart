package com.example.hayoung.mycart.model;

/**
 * Created by hayoung on 2017. 9. 13..
 */

public class CartItem {
    private String imagePath;
    private String description;

    public CartItem(String imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
