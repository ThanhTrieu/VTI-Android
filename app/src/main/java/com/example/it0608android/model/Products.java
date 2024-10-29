package com.example.it0608android.model;

public class Products {
    public int id;
    public String name;
    public String image;
    public int price;

    public Products(int id, String name, String image, int price){
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
