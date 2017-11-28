package com.example.hayoung.mycart.model;

import io.realm.RealmObject;

/**
 * Created by hayoung on 2017. 9. 13..
 */

public class CartItem extends RealmObject {
    private String imagePath;
    private String description;

    public CartItem(String imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    public CartItem() {
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
