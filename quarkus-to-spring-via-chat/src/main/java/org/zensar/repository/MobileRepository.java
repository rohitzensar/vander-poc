package org.zensar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zensar.entity.Mobile;

@Repository
public interface MobileRepository extends JpaRepository<Mobile, Integer> {

    List<Mobile> findByName(String name);

    List<Mobile> findByPrice(int price);

    List<Mobile> findByNameAndPrice(String name, int price);

    List<Mobile> findByNameIgnoreCase(String name);

    List<Mobile> findByPriceGreaterThan(int price);

    List<Mobile> findByPriceLessThan(int price);

    List<Mobile> findByPriceBetween(int minPrice, int maxPrice);

}