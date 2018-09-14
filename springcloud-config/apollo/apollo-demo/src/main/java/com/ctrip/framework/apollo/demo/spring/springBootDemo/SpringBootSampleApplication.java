package com.ctrip.framework.apollo.demo.spring.springBootDemo;

import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;


/**
 * "com.ctrip.framework.apollo.demo.spring.common",
 * @author Jason Song(song_s@ctrip.com)
 */
@SpringBootApplication(scanBasePackages = {
    "com.ctrip.framework.apollo.demo.spring.springBootDemo"
})
public class SpringBootSampleApplication {

  public static void main(String[] args) throws IOException {
    ApplicationContext context = new SpringApplicationBuilder(SpringBootSampleApplication.class).run(args);
//    AnnotatedBean annotatedBean = context.getBean(AnnotatedBean.class);
//    SampleRedisConfig redisConfig = null;
//    try {
//      redisConfig = context.getBean(SampleRedisConfig.class);
//    } catch (NoSuchBeanDefinitionException ex) {
//      System.out.println("SampleRedisConfig is null, 'redis.cache.enabled' must have been set to false.");
//    }
//
//    System.out.println("SpringBootSampleApplication Demo. Input any key except quit to print the values. Input quit to exit.");
//    while (true) {
//      System.out.print("> ");
//      String input = new BufferedReader(new InputStreamReader(System.in, Charsets.UTF_8)).readLine();
//      if (!Strings.isNullOrEmpty(input) && input.trim().equalsIgnoreCase("quit")) {
//        System.exit(0);
//      }
//
//      System.out.println(annotatedBean.toString());
//      if (redisConfig != null) {
//        System.out.println(redisConfig.toString());
//      }
//    }
  }
}
