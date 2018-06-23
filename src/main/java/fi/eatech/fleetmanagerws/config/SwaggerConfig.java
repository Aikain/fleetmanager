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
 * .
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
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
                "0.0.1-SNAPSHOT",
                "#",
                new Contact("Ville Nupponen", "https://aika.in/", "ville.nupponen@aika.in"),
                "License of API", "https://github.com/Aikain/fleetmanager/blob/master/LICENSE", Collections.emptyList());
    }
}
