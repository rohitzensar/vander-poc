package org.zensar.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.beanutils.BeanUtils;
import org.zensar.entity.Laptop;
import org.zensar.repository.LaptopRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class LaptopService {

    @Inject
    private LaptopRepository laptopRepository;

    public List<Laptop> getAllByRam(int ram) {
        return laptopRepository.listAllWithRam(ram);
    }

    public List<Laptop> getAllLaptops() {
        return laptopRepository.listAll();
    }

    public String addLaptop(Laptop laptop) {
        laptopRepository.persist(laptop);
        return laptop.getId();
    }

    public Optional<Laptop> getLaptopById(String id) {
        return laptopRepository.findByIdOptional(id);
    }

    public boolean updateLaptop(String id, Laptop newLaptop) {
        Laptop dbLaptop = getLaptopById(id).orElse(null);
        if (dbLaptop != null) {
            try {
                BeanUtils.copyProperties(dbLaptop, newLaptop);
                laptopRepository.persist(dbLaptop);
                laptopRepository.isPersistent(dbLaptop);
            } catch (Exception e) {
                // Handle any exceptions (e.g., IllegalAccessException, InvocationTargetException)
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteLaptop(String id) {
        return laptopRepository.deleteById(id);
    }
}
