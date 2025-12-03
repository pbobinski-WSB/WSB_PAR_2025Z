/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demojetty9rest.simpleRest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author DELL
 */
@Path("/items")
public class ItemsResource {

    @Context
    private UriInfo context;

    //static List<String> list = new ArrayList<String>();

    /**
     * Creates a new instance of ItemsResource
     */
    public ItemsResource() {
        

    }

    /**
     * Retrieves representation of an instance of simpleRest.ItemsResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        StringBuffer sb = new StringBuffer("[\"");
        for (String s : NewSingleton.getInstance().getList()) {
            sb.append(s + "\",\"");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    /**
     * POST method for creating an instance of ItemResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        NewSingleton.getInstance().getList().add(content);
        return Response.ok().build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public ItemResource getItemResource(@PathParam("id") String id) {
        return ItemResource.getInstance(id);
    }
}
