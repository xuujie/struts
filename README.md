# Migrate to Spring Boot

## Add Spring Boot

* add spring boot starter parent
```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.20.RELEASE</version>
    </parent>
```

* add spring boot maven plugin

```xml
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
```

* Spring Boot Application
```java
package org.superbiz.struts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StrutsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StrutsApplication.class, args);
    }
}
```

## Migrate tomee to spring boot
javaee-api implementation is at tomee server, 
tomee implements jsp and ejb, when we change to spring boot, 
we will be using tomcat, after we remove tomee we need to add jsp support and use jpa to replace openejb 

### Remove tomee dependency 
```xml
        <dependency>
            <groupId>org.apache.tomee</groupId>
            <artifactId>javaee-api</artifactId>
            <version>7.0</version>
        </dependency>
```

### add spring boot dependencies

- spring boot web
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

- jsp support
```xml
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
        </dependency>
```

- jpa
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```

### Struts Filters
Spring boot does not read web.xml for web config, so we need to convert filters defined in web.xml to FilterRegistrationBean

- FilterDispatcher is deprecated after struts 2.1.3, use StrutsPrepareAndExecuteFilter to replace FilterDispatcher
```java_holder_method_tree
    @Bean
    public FilterRegistrationBean filterDispatcherBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("struts2");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(new StrutsPrepareAndExecuteFilter());
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }
```

- Sitemesh filter
```java_holder_method_tree
    @Bean
    public FilterRegistrationBean pageFilterBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("sitemesh");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(new PageFilter());
        filterRegistrationBean.setOrder(3);

        return filterRegistrationBean;
    }
```

## Bean management
we will use spring to manage bean creation

- Add struts2 spring plugin
```xml
        <dependency>
            <groupId>org.apache.struts</groupId>
            <artifactId>struts2-spring-plugin</artifactId>
            <version>2.1.8.1</version>
        </dependency>
```
- add @Component for ActionSupport classes

### add struts-tags into all jsp using struts tags
- decorators/layout.jsp
- addUserForm.jsp
- findUserForm.jsp
```
<%@ taglib prefix="s" uri="/struts-tags" %>
```

## Database config

- add application.yml for database config
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
  h2:
    console:
      enabled: true
```

* Remove UserServiceImpl 

* Add @Repository annotation to UserService
```java_holder_method_tree
@Repository
public interface UserService extends CrudRepository<User, Long> {

    public User findById(Long id);

    public List<User> findAll();
}
```

## Clean up
* WEB-INF/web.xml
* prelude.jspf