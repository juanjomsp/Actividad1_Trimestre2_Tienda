package com.example.tiendaapp; // ¡Ojo! Asegúrate de que esto coincide con el tuyo

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String title;
    private double price;
    private String thumbnail;

    // Constructor vacío (necesario para Gson)
    public Product() {}

    // Constructor
    public Product(int id, String title, double price, String thumbnail) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getThumbnail() { return thumbnail; }
}