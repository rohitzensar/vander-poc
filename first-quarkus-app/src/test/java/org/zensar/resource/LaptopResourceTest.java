package org.zensar.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.zensar.entity.Laptop;
import org.zensar.service.LaptopService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class LaptopResourceTest {

    @Inject
    LaptopService laptopService;

    @Test
    public void testGetAllLaptops() {
        List<Laptop> laptops = List.of(new Laptop("1", "Dell", "Inspiron", 8, 256));
        given().when().get("/laptops").then().statusCode(200).body("", hasSize(1)).body("[0].id", equalTo("1")).body("[0].name", equalTo("Dell Inspiron")).body("[0].ram", equalTo(8)).body("[0].externalStorage", equalTo(256));
    }

    @Test
    public void testSaveLaptop() {
        Laptop laptop = new Laptop("1", "Dell", "Inspiron", 8, 256);
        given().contentType(MediaType.APPLICATION_JSON).body(laptop).when().post("/laptops").then().statusCode(200).header("Location", "/laptops/1");
    }

    @Test
    public void testGetLaptop() {
        Laptop laptop = new Laptop("1", "Dell", "Inspiron", 8, 256);
        laptopService.addLaptop(laptop);
        given().when().get("/laptops/1").then().statusCode(200).body("id", equalTo("1")).body("name", equalTo("Dell Inspiron")).body("ram", equalTo(8)).body("externalStorage", equalTo(256));
    }

    @Test
    public void testUpdateLaptop() {
        Laptop laptop = new Laptop("1", "Dell", "Inspiron", 8, 256);
        laptopService.addLaptop(laptop);
        Laptop newLaptop = new Laptop("1", "Dell", "Latitude", 16, 512);
        given().contentType(MediaType.APPLICATION_JSON).body(newLaptop).when().patch("/laptops/1").then().statusCode(200).header("Location", "/laptops/1");
    }

    @Test
    public void testDeleteLaptopById() {
        Laptop laptop = new Laptop("1", "Dell", "Inspiron", 8, 256);
        laptopService.addLaptop(laptop);
        given().when().delete("/laptops/1").then().statusCode(204);
    }
}