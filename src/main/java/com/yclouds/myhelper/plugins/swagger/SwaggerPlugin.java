package com.yclouds.myhelper.plugins.swagger;

import com.yclouds.myhelper.plugins.AbstractPlugin;
import com.yclouds.myhelper.plugins.swagger.SwaggerProperties.SwaggerApiInfo;
import com.yclouds.myhelper.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

/**
 * Swagger插件
 *
 * @author ye17186
 * @version 2019/6/6 15:54
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "myhelper.plugins.swagger", name = "enabled", havingValue = "true")
@EnableSwagger2
public class SwaggerPlugin extends AbstractPlugin {

    public SwaggerPlugin() {
        printLog();
    }

    @Autowired
    SwaggerProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public Docket createDocket() {

        String basePackage = properties.getBackPackage();

        return new Docket(DocumentationType.SWAGGER_2).enable(properties.isEnabled())
            .useDefaultResponseMessages(false)
            .apiInfo(createApi()).select()
            .apis(StringUtils.isEmpty(basePackage) ? RequestHandlerSelectors.any()
                : RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any())
            .build();
    }

    private ApiInfo createApi() {
        SwaggerApiInfo apiInfo = properties.getApiInfo();
        return new ApiInfoBuilder().title(apiInfo.getTitle())
            .contact(new Contact(apiInfo.getDeveloper(), apiInfo.getWebsite(), apiInfo.getEmail()))
            .version(apiInfo.getVersion()).description(apiInfo.getDescription()).build();
    }
}
