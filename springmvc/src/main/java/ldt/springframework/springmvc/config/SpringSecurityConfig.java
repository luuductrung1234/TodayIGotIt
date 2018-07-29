package ldt.springframework.springmvc.config;

import ldt.springframework.springmvc.services.sercurity.CustomAuthenticationSuccessHandler;
import ldt.springframework.springmvc.services.sercurity.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.HashMap;
import java.util.Map;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/28/18
 */

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    @Qualifier("daoAuthenticationProvider")
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;


    // =======================================
    // =            Bean Config              =
    // =======================================

    @Bean
    public String encoderId(){
        return "bcrypt";
    }

    @Bean
    public PasswordEncoder delegatingPasswordEncoder(String encoderId) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(encoderId, encoders);
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService){
        // create DaoAuthenticationProvider that interact with Service layer
        // the Service layer get User data from database and convert it to Spring Security UserDetails
        // but the UserDetails contain the encrypted password
        // so we config the Jasypt passwordEncoder for DaoAuthenticationProvider

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/H2Console").disable()

                // static resource
                .authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/css").permitAll()
                .and().authorizeRequests().antMatchers("/js").permitAll()

                // widely access resource
                .and().authorizeRequests().antMatchers("/customers").permitAll()
                .and().authorizeRequests().antMatchers("/courses").permitAll()
                .and().authorizeRequests().antMatchers("/course/show/**").permitAll()

                // authentication resources
                .and().authorizeRequests().antMatchers("/user/**").authenticated()
                .and().authorizeRequests().antMatchers("/customer/**").authenticated()
                .and().authorizeRequests().antMatchers("/course/new").authenticated()
                .and().authorizeRequests().antMatchers("/course/edit/**").authenticated()
                .and().authorizeRequests().antMatchers("/course/delete/**").authenticated()
                .and().formLogin().successHandler(customAuthenticationSuccessHandler).loginPage("/login").permitAll()
                .and().logout().logoutSuccessHandler(customLogoutSuccessHandler).permitAll()
                .and().exceptionHandling().accessDeniedPage("/access_denied");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().ignoringAntMatchers("/H2Console").disable()
//
//                // static resource
//                .authorizeRequests().antMatchers("/webjars/**").permitAll()
//                .and().authorizeRequests().antMatchers("/static/css").permitAll()
//                .and().authorizeRequests().antMatchers("/js").permitAll()
//
//                // widely access resource
//                .and().authorizeRequests().antMatchers("/customers").permitAll()
//                .and().authorizeRequests().antMatchers("/courses").permitAll()
//                .and().authorizeRequests().antMatchers("/course/show/**").permitAll()
//
//                // authentication resources
//                .and().formLogin().loginPage("/login").permitAll()
//                .and().authorizeRequests().antMatchers("/user/**").authenticated()
//                .and().authorizeRequests().antMatchers("/customer/**").authenticated()
//                .and().authorizeRequests().antMatchers("/course/new").authenticated()
//                .and().authorizeRequests().antMatchers("/course/edit/**").authenticated()
//                .and().authorizeRequests().antMatchers("/course/delete/**").authenticated()
//                .and().exceptionHandling().accessDeniedPage("/access_denied");
//    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler (){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }
}
