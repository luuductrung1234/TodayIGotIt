package ldt.springframework.springmvc.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
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
@EnableJpaRepositories("ldt.springframework.springmvc.repository.springdatarepository.data")
public class ApplicationConfig {

    @Bean
    public StrongPasswordEncryptor strongEncryptor(){
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor;
    }
}



