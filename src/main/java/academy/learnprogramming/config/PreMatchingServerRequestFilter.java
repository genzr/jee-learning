/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academy.learnprogramming.config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author genzr
 */
@Provider
@PreMatching
public class PreMatchingServerRequestFilter implements ContainerRequestFilter{
    
    @Inject
    Logger logger;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.log(Level.INFO, "Original http method was " + requestContext.getMethod());
        final String httpMethod = requestContext.getHeaderString("X-Http-Method-Override");
        if(httpMethod != null && !httpMethod.isEmpty()) {
            requestContext.setMethod(httpMethod);
            logger.log(Level.INFO, "Http method converted to " + httpMethod);
        }
    }
    
    
    
}
