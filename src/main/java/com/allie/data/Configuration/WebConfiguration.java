package com.allie.data.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
@Configuration
public class WebConfiguration {
    @Autowired
    HeadersFilter headersFilter;

}
