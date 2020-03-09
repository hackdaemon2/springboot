package com.lace;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

/**
 * 
 * @author centricgateway
 */
@Component
public class ServletInitializer extends SpringBootServletInitializer {
    
    // servlet initializer
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LaceSpringBootApp.class);
    }
}
