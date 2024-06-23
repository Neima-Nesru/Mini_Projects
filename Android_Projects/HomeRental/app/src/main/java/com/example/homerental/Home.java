package com.example.homerental;

public class Home {
    private int id;
    private String title;
    private String description;
    private double price;
    private String location;
    private String imageUri;

    public Home(int id, String title, String description, double price, String location, String imageUri) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.imageUri = imageUri;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Other getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUri() {
        return imageUri;
    }
}
