package com.example.cloudproviderhystrixpayment8003;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 18:08
 **/

@SpringBootApplication
@EnableCircuitBreaker
public class CloudProviderHystrixPayment8003Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudProviderHystrixPayment8003Application.class,args);
    }
    /**
     * 此配置是为了服务监控而配置,与服务容错本身无关, springcloud升级后的坑
     * servletRegistrationBean因为springboot的默认路径不是"/hystrix.stream
     * 只要在自己的项目里配置下面servlet就可以了
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
