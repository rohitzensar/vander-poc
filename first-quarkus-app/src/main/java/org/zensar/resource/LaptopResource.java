package org.zensar.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.beanutils.BeanUtils;
import org.zensar.entity.Laptop;
import org.zensar.service.LaptopService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/laptops")
public class LaptopResource {

    @Inject
    LaptopService laptopService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLaptops() {
        List<Laptop> laptopList = laptopService.getAllLaptops();
        return Response.ok(laptopList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveLaptop(Laptop laptop) {
        laptop.setId(String.valueOf(UUID.randomUUID()));
        String id = laptopService.addLaptop(laptop);
        if(id != null) {
            return Response.ok(URI.create("/laptops/"+id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLaptop(@PathParam("id") String id) {
        Optional<Laptop> laptop = laptopService.getLaptopById(id);
        return Response.ok(laptop).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLaptop(@PathParam("id") String id, Laptop newLaptop) {
        boolean isUpdated = laptopService.updateLaptop(id, newLaptop);
        if(isUpdated) {
            return Response.ok(URI.create("/laptops/"+id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLaptopById(@PathParam("id") String id) {
        boolean isDeleted = laptopService.deleteLaptop(id);
        if(isDeleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
