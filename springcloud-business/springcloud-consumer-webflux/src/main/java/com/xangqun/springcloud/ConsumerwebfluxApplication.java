package com.xangqun.springcloud;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableHystrix
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages = {"com.xangqun.springcloud"})
public class ConsumerwebfluxApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsumerwebfluxApplication.class);
        app.addListeners(new ApplicationPidFileWriter());//把运行的pid打印到文件里
        app.addListeners(new WebServerPortFileWriter());// the ports of the running web server
        app.run(args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule() {
        //这里配置策略，和配置文件对应
        return new RandomRule();
    }
}
