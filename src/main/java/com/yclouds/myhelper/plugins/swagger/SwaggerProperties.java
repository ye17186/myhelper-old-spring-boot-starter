package com.yclouds.myhelper.plugins.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yemeng-lhq
 * @version 2019/6/6 15:50
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "myhelper.plugins.swagger")
@Component
public class SwaggerProperties {

    /**
     * 是否启用Swagger2插件
     */
    private boolean enabled;

    /**
     * 基出包路径
     */
    private String backPackage;

    /**
     * API基本信息
     */
    private SwaggerApiInfo apiInfo = new SwaggerApiInfo();

    @Setter
    @Getter
    public class SwaggerApiInfo {

        /**
         * 标题
         */
        private String title = "Swagger2 Api Docs";

        /**
         * 开发者
         */
        private String developer = "developer";

        /**
         * 邮箱
         */
        private String email = "";

        /**
         * 主页
         */
        private String website = "http://localhost:8080";

        /**
         * 版本
         */
        private String version = "1.0.0";

        /**
         * 描述信息
         */
        private String description = "Swagger2 Api Docs";
    }
}
