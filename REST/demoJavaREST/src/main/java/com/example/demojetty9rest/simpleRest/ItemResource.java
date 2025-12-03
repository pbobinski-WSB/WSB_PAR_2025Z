/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demojetty9rest.simpleRest;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Target;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;


/**
 * REST Web Service
 *
 * @author DELL
 */
public class ItemResource {

    private String id;

    /**
     * Creates a new instance of ItemResource
     */
    private ItemResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the ItemResource
     */
    public static ItemResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ItemResource class.
        return new ItemResource(id);
    }

    /**
     * Retrieves representation of an instance of simpleRest.ItemResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "[\""+NewSingleton.getInstance().getList().get(Integer.parseInt(id))+"\"]";
    }

    /**
     * PUT method for updating or creating an instance of ItemResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
        NewSingleton.getInstance().getList().set(Integer.parseInt(id), content);
    }

    /**
     * DELETE method for resource ItemResource
     */
    @DELETE
    public void delete() {
        System.out.println("simpleRest.ItemResource.delete()");
        NewSingleton.getInstance().getList().remove(Integer.parseInt(id));
       
    }
}
