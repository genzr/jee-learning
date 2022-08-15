/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academy.learnprogramming.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author genzr
 */
@Path("departments")
public class DepartmentResource {
    
    @GET
    @Produces({"application/json; qs=0.9", "application/xml; qs=0.7"})
    public Response getDepartment() {
        return Response.ok().status(Response.Status.OK).build();
    }
    
}
