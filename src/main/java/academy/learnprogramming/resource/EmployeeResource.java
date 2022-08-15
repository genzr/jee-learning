package academy.learnprogramming.resource;

import academy.learnprogramming.entities.Employee;
import academy.learnprogramming.service.PersistenceService;
import academy.learnprogramming.service.QueryService;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("employees") // - api/v1/employees/*
@Produces("applicaton/json") // This appears to be default
@Consumes("application/json") // This appears to be default
public class EmployeeResource {
    
    @Inject
    Logger logger;
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    QueryService queryService;
    
    @Inject
    PersistenceService persistenceService;
    
    @GET    
    @Path("employees")  // - api/v1/employees/employees
    public Response getEmployees(@Context HttpHeaders httpHeaders) {
        MediaType preferedMediaType = httpHeaders.getAcceptableMediaTypes().get(0);
        return Response.ok(queryService.getEmployees(),preferedMediaType).status(Response.Status.OK).build();
    }
    
    @GET 
    @Path("employees/{id}")   // - api/v1/employees/employee/1
    public Response getEmployeeById(@PathParam("id") @DefaultValue("0") Long id, @Context Request request) {
        
        Employee employee = queryService.findEmployeeById(id); 
        
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(1000);
        
        EntityTag entityTag = new EntityTag(UUID.randomUUID().toString());
        
        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(entityTag);
        
        if(responseBuilder != null) {
            responseBuilder.cacheControl(cacheControl);
            return responseBuilder.build();
        }
        
        responseBuilder = Response.ok(employee);
        responseBuilder.tag(entityTag);
        responseBuilder.cacheControl(cacheControl);
        return responseBuilder.build();
                
    }
    
    @POST 
    @Path("employees") // - api/v1/employees/employees
    public Response createEmployee(@Valid Employee employee) {
        persistenceService.saveEmployee(employee);
        URI uri = uriInfo.getAbsolutePathBuilder().path(employee.getId().toString()).build();
        return Response.created(uri).status(Response.Status.CREATED).build();
    }
    
    @POST
    @Path("upload")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png,", "img/jpeg", "img/jpg"})
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadPicture(File picture, @QueryParam("id") @NotNull Long id) {
        Employee employee = queryService.findEmployeeById(id);
        
        try (Reader reader = new FileReader(picture)) {
            employee.setPicture(Files.readAllBytes(Paths.get(picture.toURI())));
            persistenceService.saveEmployee(employee);
            
            int totalSize = 0;
            int count = 0;
            final char [] buffer = new char[256];
            while ((count = reader.read(buffer)) != -1) {
                totalSize += count;
            }
           
            return Response.ok(totalSize).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("download")
    @Produces({MediaType.APPLICATION_OCTET_STREAM, "image/jpg", "image/png", "image/jpeg"})
    public Response getEmployeePicture(@QueryParam("id") @NotNull Long id) throws IOException {
        
        NewCookie userId = new NewCookie("userId", id.toString());
        
        Employee employee = queryService.findEmployeeById(id); 
        if(employee != null) {
            return Response.ok().entity(Files.write(Paths.get("pic.png"), employee.getPicture()).toFile()).cookie(userId).build();
        }
        
        return Response.noContent().build();
    }
 
    @DELETE
    @Path("{id}")
    public Response terminateEmployee(@PathParam("id") @NotNull Long id) {
        logger.log(Level.INFO, "DELETE endpoint invoked");
        return Response.ok().status(Response.Status.OK).build();
    }
    
}
