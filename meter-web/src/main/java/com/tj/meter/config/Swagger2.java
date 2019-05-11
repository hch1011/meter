package com.tj.meter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Restful API 访问路径:  http://localhost/meter/swagger-ui.html 
 * @author hc
 *
 */
@EnableWebMvc 
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.tj.meter.controller")
public class Swagger2 extends WebMvcConfigurationSupport{
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tj.meter.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring MVC中使用Swagger2构建RESTful APIs")
                .description("更多学习资料请关注：http://www.baidu.com/")
                .termsOfServiceUrl("http://www.baidu.com/")
                .contact("hc")
                .version("1.0")
                .build();
    }
 
}
