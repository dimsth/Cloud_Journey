package org.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

public class Main {

    private static final String BASE_URI = "http://localhost:8080/api/";

    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Jersey application started. Access it at " + BASE_URI);
        System.out.println("Hit CTRL+C to stop it...");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static HttpServer startServer() {
        ResourceConfig resourceConfig = new ResourceConfig().register(ConversionResource.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }

    @Path("/convert")
    public static class ConversionResource {

        @GET
        @Path("/inch_to_cm")
        @Produces(MediaType.APPLICATION_JSON)
        public Response convertInchToCm(@QueryParam("inch") double inch) {
            double cm = inch * 2.54;
            return Response.ok("{\"inch\": " + inch + ", \"cm\": " + cm + "}").build();
        }

        @GET
        @Path("/cm_to_inch")
        @Produces(MediaType.APPLICATION_JSON)
        public Response convertCmToInch(@QueryParam("cm") double cm) {
            double inch = cm / 2.54;
            return Response.ok("{\"cm\": " + cm + ", \"inch\": " + inch + "}").build();
        }
    }
}
