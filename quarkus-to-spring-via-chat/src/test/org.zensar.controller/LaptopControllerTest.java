package org.zensar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.zensar.entity.Laptop;
import org.zensar.service.LaptopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(LaptopController.class)
public class LaptopControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LaptopService laptopService;

    @Test
    public void testGetAllLaptops() throws Exception {
        List<Laptop> laptopList = new ArrayList<>();
        laptopList.add(new Laptop("1", "Laptop1", "Brand1", 8, 256));
        laptopList.add(new Laptop("2", "Laptop2", "Brand2", 16, 512));
        Mockito.when(laptopService.getAllLaptops()).thenReturn(laptopList);
        mockMvc.perform(MockMvcRequestBuilders.get("/laptops").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))).andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("1"))).andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Laptop1"))).andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", Matchers.is("Brand1"))).andExpect(MockMvcResultMatchers.jsonPath("$[0].ram", Matchers.is(8))).andExpect(MockMvcResultMatchers.jsonPath("$[0].externalStorage", Matchers.is(256))).andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is("2"))).andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Laptop2"))).andExpect(MockMvcResultMatchers.jsonPath("$[1].brand", Matchers.is("Brand2"))).andExpect(MockMvcResultMatchers.jsonPath("$[1].ram", Matchers.is(16))).andExpect(MockMvcResultMatchers.jsonPath("$[1].externalStorage", Matchers.is(512)));
    }

    @Test
    public void testSaveLaptop() throws Exception {
        Laptop laptop = new Laptop("1", "Laptop1", "Brand1", 8, 256);
        Mockito.when(laptopService.addLaptop(Mockito.any(Laptop.class))).thenReturn("1");
        mockMvc.perform(MockMvcRequestBuilders.post("/laptops").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(laptop))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("/laptops/1"));
//        mockMvc.perform(MockMvcRequestBuilders.post("/laptops").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(laptop))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Location", "/laptops/1"));
    }

    @Test
    public void testGetLaptop() throws Exception {
        Laptop laptop = new Laptop("1", "Laptop1", "Brand1", 8, 256);
        Mockito.when(laptopService.getLaptopById("1")).thenReturn(Optional.of(laptop));
        mockMvc.perform(MockMvcRequestBuilders.get("/laptops/1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("1"))).andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Laptop1"))).andExpect(MockMvcResultMatchers.jsonPath("$.brand", Matchers.is("Brand1"))).andExpect(MockMvcResultMatchers.jsonPath("$.ram", Matchers.is(8))).andExpect(MockMvcResultMatchers.jsonPath("$.externalStorage", Matchers.is(256)));
    }

    @Test
    public void testUpdateLaptop() throws Exception {
        Laptop laptop = new Laptop("1", "Laptop1", "Brand1", 8, 256);
        Mockito.when(laptopService.updateLaptop(Mockito.anyString(), Mockito.any(Laptop.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/laptops/1").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(laptop))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.header().string("Location", "/laptops/1"));
    }

    @Test
    public void testDeleteLaptopById() throws Exception {
        Mockito.when(laptopService.deleteLaptop("1")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/laptops/1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
