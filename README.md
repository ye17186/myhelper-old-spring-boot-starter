# myhelper-spring-boot-starter
Spring Boot with MyHelper support, help you simplify develop in Sprint Boot web project.

**内置工具类：**
1. 时间工具类DateUtils，用于处理时间，包括JDK8中的时间
2. ID生成工具类IdGenUtils，用于生成全局唯一的ID，内置了基于内存和基于Redis的两种算法
3. Json格式化工具类JsonUtils，基于Jackson的json处理
4. Redis工具类RedisUtils，封装了普通Object、Set、List、Map多种对象的处理方法
5. 字符串工具类StringUtils，扩展至org.apache.commons.lang3.StringUtils，提供常用的字符串处理方法
6. Spring上下文工具类SpringUtils，分装了一套静态方法，用于随时随地的取出Spring上下文中的Bean对象

**内置插件：**
1. TokenConfiguration
提供了基于token、timestamp、nonce、signature接口安全处理，此插件依赖于Redis
启用方式: myhelper.plugins.token.enabled=true
2. ControllerAdviceConfiguration
提供了控制器的增强处理，用于同一异常处理
启用方式: myhelper.plugins.advice.enabled=true
