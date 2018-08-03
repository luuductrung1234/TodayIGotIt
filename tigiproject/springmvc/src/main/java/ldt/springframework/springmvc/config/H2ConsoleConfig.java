package ldt.springframework.springmvc.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/31/18
 */

@Configuration
public class H2ConsoleConfig {
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        // H2 Database Console Config
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/H2Console/*");
        return registrationBean;
    }
}
