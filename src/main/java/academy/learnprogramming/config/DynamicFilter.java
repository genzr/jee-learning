/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academy.learnprogramming.config;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;

/**
 *
 * @author genzr
 */
public class DynamicFilter implements ContainerResponseFilter{

    int age;

    public DynamicFilter(int age) {
        this.age = age;
    }

    public DynamicFilter() {
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if(requestContext.getMethod().equalsIgnoreCase("GET")) {
            CacheControl cacheControl = new CacheControl();
            cacheControl.setMaxAge(age);
            responseContext.getHeaders().add("Cache-Control", cacheControl);
            responseContext.getHeaders().add("DynamicMessage", "Here is my message!");
        }
    }
    
    
}
