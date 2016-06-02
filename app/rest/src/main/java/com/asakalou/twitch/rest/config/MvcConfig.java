package com.asakalou.twitch.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@ComponentScan({"com.asakalou.twitch.rest.controller", "com.asakalou.twitch.rest.config"})
@Import(RestConfig.class)
//@ImportResource({
//        "classpath:properties.xml"
//})
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final int cacheSeconds = Integer.MAX_VALUE;
    private static String[] resources = new String[]{"resources",
            "javascripts", "stylesheets", "images", "fonts", "bower_components"};

    private static String[] apps = new String[]{null, "admin", "a"};


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/WEB-INF/views/**").addResourceLocations("/views/");

        for (String app : apps) {
            for (String res : resources) {
                String appPrefix = app != null ? "/" + app : "";
                registry
                        .addResourceHandler(appPrefix + "/" + res + "/**")
                        .addResourceLocations("/" + res + "/")
                        .setCachePeriod(cacheSeconds);
            }
        }
        registry.setOrder(-1);
    }

//    @Bean
//    public InternalResourceViewResolver jspViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setPrefix("/WEB-INF/views/");
//        bean.setSuffix(".html");
//        return bean;
//    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }

}
