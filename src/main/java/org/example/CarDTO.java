package org.example;

import java.math.BigDecimal;

public class CarDTO {
    private int id;
    private CarModelDTO model;
    private Dealership dealership;
    private CarStatus status;
    private String configuration;
    private String color;
    private BigDecimal price;

    public enum CarStatus {
        AVAILABLE, IN_TRANSIT, SOLD, RESERVED, ON_HOLD
    }

    public CarDTO(int id, CarModelDTO model, Dealership dealership, CarStatus status,
                  String configuration, String color, BigDecimal price) {
        this.id = id;
        this.model = model;
        this.dealership = dealership;
        this.status = status;
        this.configuration = configuration;
        this.color = color;
        this.price = price;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public CarModelDTO getModel() { return model; }
    public Dealership getDealership() { return dealership; }
    public CarStatus getStatus() { return status; }
    public String getConfiguration() { return configuration; }
    public String getColor() { return color; }
    public BigDecimal getPrice() { return price; }

    public void setDealership(Dealership dealership) {
        this.dealership = dealership;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", model=" + model +
                ", dealership=" + (dealership != null ? dealership.getName() : "None") +
                ", status=" + status +
                ", configuration='" + configuration + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }
}
