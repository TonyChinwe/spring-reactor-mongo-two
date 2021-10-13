package com.phisoft.springreactormongoone.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class ProjectConfiguration {


    /**
     * {@inheritDoc}
     */
    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/employee/**"))
                .apis(RequestHandlerSelectors.basePackage("com.phisoft"))
                .build()
                .apiInfo(apiDetails());
    }

    /**
     * Configures the swagger UI
     * @return the Api details needed for swagger configuration
     */

    private ApiInfo apiDetails(){
        return new ApiInfo("Employee Api ","This is an Employee Api that demonstrate the use of project reactor in building reactive web Api"+
                ".For the web, it uses spring webflux. For database storage, it uses reactive mongo db ","1.0","Free to use",
                new springfox.documentation.service.Contact("Replace","Replace","Replace"),
                "Api Licence","Replace", Collections.emptyList());
    }



}
