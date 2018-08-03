package ldt.springframework.springmvc.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 */


@Configuration
@EntityScan("ldt.springframework.tigibusiness.domain")
@ComponentScan(
        value = {
                "ldt.springframework.tigibusiness.enums",
                "ldt.springframework.tigibusiness.commands",
                "ldt.springframework.tigibusiness.repository",
                "ldt.springframework.tigibusiness.services",
                "ldt.springframework.tigibusiness.event.security",
                "ldt.springframework.tigibusiness.security"})
@EnableJpaRepositories("ldt.springframework.tigibusiness.repository.springdatarepository.data")
public class ApplicationConfig {

    @Bean
    public StrongPasswordEncryptor strongEncryptor(){
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor;
    }
}



