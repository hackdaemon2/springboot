package com.lace;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @author centricgateway
 */
public class ServletInitializer extends SpringBootServletInitializer {
    
    // servlet initializer
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LaceSpringBootApp.class);
    }
}
