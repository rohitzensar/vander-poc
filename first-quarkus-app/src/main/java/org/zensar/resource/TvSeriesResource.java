package org.zensar.resource;

import jakarta.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.zensar.client.TvSeriesIdProxy;
import org.zensar.model.TvSeries;

@Path("/tvseries")
public class TvSeriesResource {

    @RestClient
    TvSeriesIdProxy proxy;

    @GET
    @Path("/{id}")
    @Fallback(fallbackMethod = "getTvSeriesByIdFallback")
    @Retry(maxRetries = 3)
//    @Timeout(1000)
    @CircuitBreaker(
        requestVolumeThreshold=4,
        failureRatio=0.5,
//        successThreshold=,
//        failOn = ,
//        skipOn = ,
            delay=1000
    )
    public Response getTvSeriesById(@PathParam("id") int id) {
        return Response.ok(proxy.getTvSeriesById(id)).build();
    }

    public Response getTvSeriesByIdFallback(int id) {
        return Response.ok("Site is under Maintenance").build();
    }

    @GET
    @Path("/person/{personname}")
    public JsonArray getTvSeriesById(@PathParam("personname") String personName) {
        return proxy.getTvSeriesByPersonName(personName);
    }
}
