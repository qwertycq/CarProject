package org.example;

import java.util.Objects;

public class CarModelDTO {
    private String brand;
    private String model;
    private String countryOrigin;
    private String countryCode;

    public CarModelDTO(String brand, String model, String countryOrigin, String countryCode) {
        this.brand = brand;
        this.model = model;
        this.countryOrigin = countryOrigin;
        this.countryCode = countryCode;
    }

    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getCountryOrigin() { return countryOrigin; }
    public String getCountryCode() { return countryCode; }

    @Override
    public String toString() {
        return "CarModelDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", countryOrigin='" + countryOrigin + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModelDTO that = (CarModelDTO) o;
        return brand.equals(that.brand) && model.equals(that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model);
    }
}
