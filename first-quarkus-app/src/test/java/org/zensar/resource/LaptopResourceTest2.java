package org.zensar.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.zensar.entity.Laptop;
import org.zensar.repository.LaptopRepository;
import org.zensar.service.LaptopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
public class LaptopResourceTest2 {

    @InjectMock
    LaptopRepository laptopRepository;

    @Inject
    LaptopService laptopService;

    @Test
    public void testGetAllLaptops() {
        List<Laptop> laptopList = new ArrayList<>();
        laptopList.add(new Laptop("1", "Laptop1", "Brand1", 8, 256));
        laptopList.add(new Laptop("2", "Laptop2", "Brand2", 16, 512));

        Mockito.when(laptopRepository.listAll()).thenReturn(laptopList);

        given()
                .when().get("/laptops")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body("", hasSize(2))
                .body("[0].id", equalTo("1"))
                .body("[0].name", equalTo("Laptop1"))
                .body("[0].brand", equalTo("Brand1"))
                .body("[0].ram", equalTo(8))
                .body("[0].externalStorage", equalTo(256))
                .body("[1].id", equalTo("2"))
                .body("[1].name", equalTo("Laptop2"))
                .body("[1].brand", equalTo("Brand2"))
                .body("[1].ram", equalTo(16))
                .body("[1].externalStorage", equalTo(512));
    }

    @Test
    public void testSaveLaptop() {
        Laptop laptop = new Laptop("1", "Laptop1", "Brand1", 8, 256);

        Mockito.doNothing().when(laptopRepository).persist(laptop);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(laptop)
                .when().post("/laptops")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(MediaType.TEXT_PLAIN)
                .body(equalTo("/laptops/1"));
//                .header("Location", "/laptops/1");
    }

    @Test
    public void testGetLaptop() {
        Laptop laptop = new Laptop("1", "Laptop1", "Brand1", 8, 256);

        Mockito.when(laptopRepository.findByIdOptional("1")).thenReturn(Optional.of(laptop));

        given()
                .when().get("/laptops/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body("id", equalTo("1"))
                .body("name", equalTo("Laptop1"))
                .body("brand", equalTo("Brand1"))
                .body("ram", equalTo(8))
                .body("externalStorage", equalTo(256));
    }

    @Test
    public void testUpdateLaptop() {
        Laptop laptop = new Laptop("1", "Laptop1", "Brand1", 8, 256);
        laptopService.addLaptop(laptop);
        Mockito.when(laptopRepository.findByIdOptional(anyString())).thenReturn(Optional.of(laptop));
        Mockito.doNothing().when(laptopRepository).persist(laptop);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(laptop)
                .when().patch("/laptops/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
//                .header("Location", "/laptops/1");
                .body(equalTo("/laptops/1"));
        laptopRepository.deleteById("1");
    }
}
