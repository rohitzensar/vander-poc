package org.zensar.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.zensar.entity.Laptop;
import org.zensar.service.LaptopService;

@RestController
@RequestMapping("/laptops")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping
    public ResponseEntity<List<Laptop>> getAllLaptops() {
        List<Laptop> laptopList = laptopService.getAllLaptops();
        return ResponseEntity.ok(laptopList);
    }

    @PostMapping
    // original
//    public ResponseEntity<Void> saveLaptop(@RequestBody Laptop laptop) {
    public ResponseEntity<String> saveLaptop(@RequestBody Laptop laptop) {
        laptop.setId(String.valueOf(UUID.randomUUID()));
        String id = laptopService.addLaptop(laptop);
        if (id != null) {
            // original
//            return ResponseEntity.created(URI.create("/laptops/" + id)).build();
            return ResponseEntity.ok(URI.create("/laptops/" + id).toString());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Original
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Laptop>> getLaptop(@PathVariable String id) {
//        Optional<Laptop> laptop = laptopService.getLaptopById(id);
//        return ResponseEntity.ok(laptop);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Laptop> getLaptop(@PathVariable String id) {
        Optional<Laptop> laptop = laptopService.getLaptopById(id);
        return ResponseEntity.ok(laptop.orElse(null));
    }

    @PatchMapping("/{id}")
//    Original
//    public ResponseEntity<Void> updateLaptop(@PathVariable String id, @RequestBody Laptop newLaptop) {
    public ResponseEntity<String> updateLaptop(@PathVariable String id, @RequestBody Laptop newLaptop) {
        boolean isUpdated = laptopService.updateLaptop(id, newLaptop);
        if (isUpdated) {
            return ResponseEntity.ok().location(URI.create("/laptops/" + id)).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    // original
    // public ResponseEntity<Void> deleteLaptopById(@PathVariable String id) {
    public ResponseEntity<?> deleteLaptopById(@PathVariable String id) {
        boolean isDeleted = laptopService.deleteLaptop(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

}