package org.zensar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Laptop {

    @Id
    private String id;

    private String name;

    private String brand;

    private int ram;

    private int externalStorage;

    // later added
    public Laptop(String id, String name, String brand, int ram, int externalStorage) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.ram = ram;
        this.externalStorage = externalStorage;
    }
}