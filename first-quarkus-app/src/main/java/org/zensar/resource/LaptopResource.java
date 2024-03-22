package org.zensar.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.zensar.entity.Laptop;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/laptops")
public class LaptopResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLaptops() {
        List<Laptop> laptopList = Laptop.listAll();
        return Response.ok(laptopList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveLaptop(Laptop laptop) {
        Laptop.persist(laptop);
        if(laptop.isPersistent()) {
            return Response.ok(URI.create("/laptops/"+laptop.id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLaptop(@PathParam("id") Long id) {
        Optional<Laptop> laptop = Laptop.findByIdOptional(id);
        return Response.ok(laptop).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLaptop(@PathParam("id") Long id, Laptop newLaptop) {
        Optional<Laptop> dbLaptop = Laptop.findByIdOptional(id);
        if(dbLaptop.isPresent()) {
            Laptop l = dbLaptop.get();
            l.setName(newLaptop.getName() == null ? l.getName() : newLaptop.getName());
            l.persist();
            if(l.isPersistent()) {
                return Response.ok(URI.create("/laptops/"+l.id)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLaptopById(@PathParam("id") Long id) {
        boolean isDeleted = Laptop.deleteById(id);
        if(isDeleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
