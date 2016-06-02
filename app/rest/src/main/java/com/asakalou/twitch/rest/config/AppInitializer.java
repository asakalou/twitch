package com.asakalou.twitch.rest.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;


public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(MvcConfig.class);
        container.addListener(new ContextLoaderListener(rootContext));

        DelegatingFilterProxy corsFilter = new DelegatingFilterProxy(new CORSFilter());
        corsFilter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        container.addFilter("filter-cors", corsFilter).addMappingForUrlPatterns(null, false, "/*");

        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();

        ServletRegistration.Dynamic dispatcher = container.addServlet("twitch-server-rest-dispatcher",
                new DispatcherServlet(dispatcherServlet));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }

}
