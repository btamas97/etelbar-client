package com.example.dreamer.etelbarclient;

public class CartItem {
    private String image;
    private String name;
    private int price;

    public CartItem(String image, String name, int price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
