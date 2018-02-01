package com.ddebbie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
//War deployment code line DDEBBIE_PROPERTIES_PATH=
@PropertySource(value = {"file:${DDEBBIE_PROPERTIES_PATH}/application.properties"}, ignoreResourceNotFound=false)
public class DDEBBIEApplication extends SpringBootServletInitializer {
    
/*    @Value("${spring.thymleaf.cache.period}") 
    private Long thymeleafCachePeriod;*/

    /**
     * This method is for war deployment
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DDEBBIEApplication.class);
    }
    
       @Bean
    @Description("Spring message resolver")
    public ResourceBundleMessageSource messageSource() {  
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();  
        messageSource.setBasename("i18n/messages");  
        return messageSource;  
    }
    
    /* Raju Nune - added to set caching resources on server */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .setCachePeriod(0);
        
    }
   
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DDEBBIEApplication.class, args);
    }


}
