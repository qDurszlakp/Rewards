package com.example.rewards.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi(@Value("${rewards.version}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("Rewards")
                        .description("Rewards rest API")
                        .version(version));
    }

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    @Controller
    static class SwaggerController {

        @GetMapping("/swagger-ui")
        public String getSwaggerUi() {
            return "redirect:/swagger-ui/index.html";
        }

        @GetMapping("/swagger-ui/")
        public String getSwaggerUiSlash() {
            return "redirect:/swagger-ui/index.html";
        }
    }
}
