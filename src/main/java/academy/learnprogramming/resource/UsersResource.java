/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academy.learnprogramming.resource;

import academy.learnprogramming.entities.ApplicationUser;
import javax.faces.annotation.HeaderMap;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author genzr
 */
@Path("users")
@Consumes("application/json")
@Produces("application/json")
public class UsersResource {
    
    @POST 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("form") // - api/v1/users/form
    public Response createNewUser(@FormParam("email") String email, @FormParam("password") String password, @HeaderParam("Referer") String referer){
        return null;
    }
    
    @POST
    @Path("map") // - api/v1/users/map
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(MultivaluedMap<String, String> formMap, @Context HttpHeaders httpHeaders) {
        String refererString = httpHeaders.getHeaderString("Referer");
        String email =formMap.getFirst("email");
        String password = formMap.getFirst("password");
        return null;
    }
    
    @POST
    @Path("bean") // - api/v1/users/bean
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(@BeanParam ApplicationUser applicationUser, @CookieParam("user") String userCookie) {
        return null;
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserById(@PathParam("id") Long id) {
        
        return null;
    }
    
}
