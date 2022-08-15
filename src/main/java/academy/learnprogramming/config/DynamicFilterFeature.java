/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academy.learnprogramming.config;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author genzr
 */
@Provider
public class DynamicFilterFeature implements DynamicFeature{

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        MaxAge annotation = resourceInfo.getResourceMethod().getAnnotation(MaxAge.class);
    
        if(annotation != null) {
            DynamicFilter dynamicFilter = new DynamicFilter(annotation.age());
            featureContext.register(dynamicFilter);
        }
    }
    
}
