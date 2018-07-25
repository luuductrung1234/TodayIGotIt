package ldt.springframework.springmvc.config;

import org.h2.server.web.WebServlet;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 */


@Configuration
@EnableJpaRepositories("ldt.springframework.springmvc.data")
public class WebConfiguration {
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/H2Console/*");
        return registrationBean;
    }

    @Bean
    public StrongPasswordEncryptor strongEncryptor(){
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor;
    }
}



