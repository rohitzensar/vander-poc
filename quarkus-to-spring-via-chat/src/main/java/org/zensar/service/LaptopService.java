package org.zensar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.zensar.entity.Laptop;
import org.zensar.repository.LaptopRepository;

@Service
@Transactional
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    public List<Laptop> getAllByRam(int ram) {
        return laptopRepository.findByRam(ram);
    }

    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    public String addLaptop(Laptop laptop) {
        laptopRepository.save(laptop);
        return laptop.getId();
    }

    public Optional<Laptop> getLaptopById(String id) {
        return laptopRepository.findById(id);
    }

    public boolean updateLaptop(String id, Laptop newLaptop) {
        Optional<Laptop> dbLaptopOptional = getLaptopById(id);
        if (dbLaptopOptional.isPresent()) {
            Laptop dbLaptop = dbLaptopOptional.get();
            try {
                BeanUtils.copyProperties(dbLaptop, newLaptop);
                laptopRepository.save(dbLaptop);
                return true;
            } catch (Exception e) {
                // Handle any exceptions (e.g., IllegalAccessException, InvocationTargetException)
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteLaptop(String id) {
        try {
            laptopRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            // Handle any exceptions (e.g., EmptyResultDataAccessException)
            e.printStackTrace();
        }
        return false;
    }

}