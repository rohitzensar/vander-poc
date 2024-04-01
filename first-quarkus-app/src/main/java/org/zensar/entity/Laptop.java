package org.zensar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Laptop {

    @Id
    String id;

    String name;
    String brand;
    int ram;
    int externalStorage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getExternalStorage() {
        return externalStorage;
    }

    public void setExternalStorage(int externalStorage) {
        this.externalStorage = externalStorage;
    }

    public Laptop(String id, String name, String brand, int ram, int externalStorage) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.ram = ram;
        this.externalStorage = externalStorage;
    }

    public Laptop() {

    }
}
