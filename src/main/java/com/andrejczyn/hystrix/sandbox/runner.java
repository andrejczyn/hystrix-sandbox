package com.andrejczyn.hystrix.sandbox;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@EnableAutoConfiguration
public class Runner {

    private static class Command extends HystrixCommand<String> {
        protected Command() {
            super(HystrixCommandGroupKey.Factory.asKey("Test"));
        }

        @Override
        protected String run() throws Exception {
            return "OK";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

    @RequestMapping("/")
    @ResponseBody
    public String command() {
        return new Command().execute();
    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
    }

}
