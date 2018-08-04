package ldt.springframework.tigirestapi.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    @Primary
    public DataSource dataSource(){
        return  DataSourceBuilder
                .create()
                .username("root")
                .password("Trung1997")
                .url("jdbc:mysql://localhost:3306/TigiDB?autoReconnect=true&useSSL=false")
                .build();
    }


    @Bean
    public DataSource h2DataSource(){
        return  DataSourceBuilder
                .create()
                .username("sa")
                .password("")
                .url("jdbc:h2:~/test")
                .build();
    }
}
