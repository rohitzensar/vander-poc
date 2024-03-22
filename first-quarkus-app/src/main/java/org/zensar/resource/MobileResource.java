package org.zensar.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.zensar.model.Mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/mobile")
public class MobileResource {

    List<Mobile> mobileList = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMobiles() {
        return Response.ok(mobileList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMobile(Mobile mobile) {
        mobileList.add(mobile);
        return Response.ok(mobile).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMobile(@PathParam("id") Integer oldMobileId,
                                 Mobile newMobile) {
        mobileList = mobileList.stream()
                .map(mobile -> mobile.getId() == oldMobileId ? newMobile : mobile)
                .collect(Collectors.toList());

        return Response.ok(mobileList).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMobile(@PathParam("id") Integer mobileId) {
        Optional<Mobile> removeMobile = mobileList.stream()
                .filter(m -> m.getId() == mobileId)
                .findFirst();

        if (removeMobile.isPresent()) {
            boolean isRemoved = mobileList.remove(removeMobile.get());
            if (isRemoved) {
                return Response.ok(mobileList).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
