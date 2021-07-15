package com.example.eurekaprovider.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 10:48
 **/

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置druid监控
     * @return
     */
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>();
        StatViewServlet statViewServlet = new StatViewServlet();
        bean.setServlet(statViewServlet);
        bean.setUrlMappings(Collections.singleton("/druid/*"));
        HashMap<String, String> params = new HashMap<>();
        params.put("loginUsername","admin");
        params.put("loginPassword","admin");
        params.put("allow","");
        bean.setInitParameters(params);

        return bean;
    }

    /**
     * 配置web监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String, String> param = new HashMap<>();
        param.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(param);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }


}
