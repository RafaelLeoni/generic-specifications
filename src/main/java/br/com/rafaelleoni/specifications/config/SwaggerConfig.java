package br.com.rafaelleoni.specifications.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("br.com.rafaelleoni.specifications.resources"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(true);
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Phone insurance REST API")
			.description("This is an API to test generic specifications.")
			.version("1.0.0")
			.contact(new Contact("Rafael Leoni", "", "falecom@leoni.com.br"))
			.extensions(Collections.emptyList())
		.build();
    }

}