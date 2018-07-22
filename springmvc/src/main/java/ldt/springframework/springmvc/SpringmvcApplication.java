package ldt.springframework.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringmvcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringmvcApplication.class, args);

//        System.out.println("Beans k********************");
//        System.out.println(ctx.getBeanDefinitionCount());
//        System.out.println(ctx.getBeanDefinitionNames());
//
//        System.out.println("Beans Details ********************");
//        for (String name :
//                ctx.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
    }
}
