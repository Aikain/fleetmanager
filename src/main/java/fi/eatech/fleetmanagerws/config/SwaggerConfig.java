package fi.eatech.fleetmanagerws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Configuration for Swagger
 *
 * @author Ville Nupponen
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Version 1.0")
                .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.ant("/car/**"))
                    .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "FleetManager REST API",
                "Swagger documentation for FleetManager REST API.",
                "1.0.0",
                "#",
                new Contact("Ville Nupponen", "https://aika.in/", "ville.nupponen@aika.in"),
                "License of API", "https://github.com/Aikain/fleetmanager/blob/master/LICENSE", Collections.emptyList());
    }
}
