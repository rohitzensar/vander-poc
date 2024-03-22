package org.zensar.client;

import jakarta.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.Query;
import org.zensar.model.TvSeries;

@RegisterRestClient(baseUri = "https://api.tvmaze.com")
@Path("/")
public interface TvSeriesIdProxy {

    @GET
    @Path("/shows/{id}")
    TvSeries getTvSeriesById(@PathParam("id") int id);

    @GET
    @Path("/search/person")
    JsonArray getTvSeriesByPersonName(@QueryParam("q") String personName);

}
