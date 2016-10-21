package com.allie.data.Configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HeadersFilter headersFilter = new HeadersFilter();
        registrationBean.setFilter(headersFilter);
        registrationBean.addUrlPatterns("/allie-data/*");

        return registrationBean;
    }
}
