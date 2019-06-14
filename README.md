# myhelper-spring-boot-starter
Spring Boot with MyHelper support, help you simplify develop in Sprint Boot web project.

**内置工具类：**
1. DateUtils：时间工具类，用于处理时间，包括JDK8中的时间
2. IdGenUtils：ID生成工具类，用于生成全局唯一的ID，内置了基于内存和基于Redis的两种算法
3. JsonUtils：Json格式化工具类，基于Jackson的json处理
4. RedisUtils：Redis操作工具类，封装了普通Object、Set、List、Map多种对象的处理方法
5. StringUtils：字符串工具类，扩展至org.apache.commons.lang3.StringUtils，提供常用的字符串处理方法
6. SpringUtils：Spring上下文工具类，封装了一套静态方法，用于随时随地的取出Spring上下文中的Bean对象
7. ValidUtils：参数校验工具类，用于手动校验参数的合法性

**内置插件：**
1. TokenPlugin
提供了基于token、timestamp、nonce、signature接口安全处理，此插件依赖于Redis
启用方式: myhelper.plugins.token.enabled=true
2. ControllerAdvicePlugin
提供了控制器的增强处理，用于统一异常处理
启用方式: myhelper.plugins.controller-advice.enabled=true
3. SwaggerPlugin
提供了Swagger在线文档功能，只需要在yml中简单配置，即可集成Swagger2
启用方式: myhelper.plugins.swagger.enabled=true

**Web增强：**
1. EnumValid、EnumValidator用于校验枚举类型的入参
2. ApiRes、ApiResp项目统一的入参及响应类型
3. StringToDateConverter等，字符串类型参数自动绑定后台Date、LocalDateTime类型


#Start

pom.xml加入依赖集合
```
<dependency>
  <groupId>com.yclouds</groupId>
  <artifactId>myhelper-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```
application.yml开启指定插件
```
myhelper:
  plugins:
    token:
      enabled: true
      secret-key: helloWorld
      valid-duration: 3600
    controller-advice:
      enabled: true
    swagger:
      enabled: true
```

