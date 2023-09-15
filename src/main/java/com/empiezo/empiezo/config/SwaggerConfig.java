package com.empiezo.empiezo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("EMPIEZO API DOCS")
                .description("Swagger를 이용한 EMPIEZO API 문서입니다. ssr 방식으로 개발했기 때문에 직접 view를 뿌려주는 메소드는 작성하지 않았습니다.")
                .version("1.0.0");
    }
}
