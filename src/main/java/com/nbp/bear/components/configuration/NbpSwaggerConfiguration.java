package com.nbp.bear.components.configuration;

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

@Configuration
@EnableSwagger2
public class NbpSwaggerConfiguration {

    @Bean
    Docket docket () {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.nbp.bear.components.controller"))
                .build()
                .apiInfo(getApiInfos());
    }

    private ApiInfo getApiInfos() {
        return new ApiInfo(
                "Nbp Microservices Authorization/Authentification",
                "Microservices for Angular Components Integration",
                "v1.0.0", "https://github.com/randrin/Server-Components",
                new Contact("IT Software Vigevano", "https://randrin-nzeukang.netlify.app/#about_me", "nzeukangrandrin@gmail.com"),
                "Terms of Use Licence",
                "https://github.com/randrin/Server-Components/blob/master/README.md",
                Collections.emptyList());
    }
}
