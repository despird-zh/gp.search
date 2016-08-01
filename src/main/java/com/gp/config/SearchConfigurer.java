package com.gp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by garydiao on 8/1/16.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.gp.search"
})
public class SearchConfigurer {


}
