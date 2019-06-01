package org.superbiz.struts;

import com.opensymphony.module.sitemesh.filter.PageFilter;
import org.apache.struts2.dispatcher.ActionContextCleanUp;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrutsFilterConfig {

    @Bean
    public FilterRegistrationBean filterDispatcherBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("struts2");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(new StrutsPrepareAndExecuteFilter());
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }

//    @Bean
//    public FilterRegistrationBean actionContextCleanUpBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setName("struts-cleanup");
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.setFilter(new ActionContextCleanUp());
//        filterRegistrationBean.setOrder(2);
//
//        return filterRegistrationBean;
//    }

    @Bean
    public FilterRegistrationBean pageFilterBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("sitemesh");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(new PageFilter());
        filterRegistrationBean.setOrder(3);

        return filterRegistrationBean;
    }

}
