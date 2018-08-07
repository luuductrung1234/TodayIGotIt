package ldt.springframework.tigirestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/31/18
 */


@Configuration
@PropertySource("classpath:/personalconfig.properties")
public class DataSourceConfig {
    // =======================================
    // =          Attribute Section          =
    // =======================================

    @Autowired
    Environment env;

    @Bean
    @Primary
    public DataSource dataSource(){
        return  DataSourceBuilder
                .create()
                .username(env.getProperty("datasource.username"))
                .password(env.getProperty("datasource.password"))
                .url(env.getProperty("datasource.url"))
                .build();
    }
}
