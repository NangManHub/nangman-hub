package com.nangman.delivery.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import java.util.List;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(apiInfo());
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
            operation.addParametersItem(new HeaderParameter()
                        .name("X-User-Id")  // 헤더 이름
                        .description("유저 ID")
                        .required(true)
                        .schema(new StringSchema()))
                    .addParametersItem(new HeaderParameter()
                        .name("X-User-Role")  // 헤더 이름
                        .description("유저 Role")
                        .required(true)
                        .schema(new StringSchema()));
        }));
    }

    private Info apiInfo() {
        return new Info().title("NangManHub Delivery API")
                .description("NangManHub Delivery API Documentation")
                .version("1.0.0");
    }
}