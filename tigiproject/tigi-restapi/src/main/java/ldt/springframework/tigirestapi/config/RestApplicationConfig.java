package ldt.springframework.tigirestapi.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/1/18
 */


@Configuration
@EntityScan("ldt.springframework.springmvc.domain")
@ComponentScan(
        value = {
                "ldt.springframework.springmvc.enums",
                "ldt.springframework.springmvc.commands",
                "ldt.springframework.springmvc.repository",
                "ldt.springframework.springmvc.services",
                "ldt.springframework.springmvc.event.security",
                "ldt.springframework.springmvc.sercurity",
                "ldt.springframework.springmvc.sercurity.encrypt"})
@EnableJpaRepositories("ldt.springframework.springmvc.repository.springdatarepository.data")
public class RestApplicationConfig {
}
