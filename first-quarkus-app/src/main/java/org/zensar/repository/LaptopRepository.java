package org.zensar.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.zensar.entity.Laptop;

import java.util.List;

@ApplicationScoped
public class LaptopRepository implements PanacheRepositoryBase<Laptop, String> {

    public List<Laptop> listAllWithRam(int ram) {
        return list("ram", ram);
    }
}
