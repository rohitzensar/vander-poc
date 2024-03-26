package org.zensar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Mobile {

    @Id
    private Integer id;

    private String name;

    private int price;

}