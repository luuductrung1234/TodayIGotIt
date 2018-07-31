package ldt.springframework.springmvc.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/31/18
 */


@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        return  DataSourceBuilder
                .create()
                .username("root")
                .password("Trung1997")
                .url("jdbc:mysql://localhost:3306/TigiDB?autoReconnect=true&useSSL=false")
                .build();
    }
}
