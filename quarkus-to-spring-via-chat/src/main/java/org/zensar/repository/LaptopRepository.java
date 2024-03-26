package org.zensar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.zensar.entity.Laptop;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, String> {

    List<Laptop> findByRam(int ram);

    List<Laptop> findByName(String name);

    List<Laptop> findByBrand(String brand);

    List<Laptop> findByExternalStorage(int externalStorage);

}
