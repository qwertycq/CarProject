package task1_3;

import java.util.List;

public class Dealership {
    private String name;
    private List<CarDTO> cars;

    public Dealership(String name, List<CarDTO> cars) {
        this.name = name;
        this.cars = cars;
    }

    public String getName() { return name; }
    public List<CarDTO> getCars() { return cars; }

    @Override
    public String toString() {
        return "Dealership{" +
                "name='" + name + '\'' +
                ", cars=" + cars +
                '}';
    }
}
