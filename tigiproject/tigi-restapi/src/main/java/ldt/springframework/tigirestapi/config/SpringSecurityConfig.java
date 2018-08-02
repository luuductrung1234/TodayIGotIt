package ldt.springframework.tigirestapi.config;

import ldt.springframework.springmvc.enums.RoleType;
import ldt.springframework.springmvc.sercurity.CustomAuthenticationSuccessHandler;
import ldt.springframework.springmvc.sercurity.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/28/18
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

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
                .and().authorizeRequests().antMatchers("/customer/**").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/course/new").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().authorizeRequests().antMatchers("/course/edit/**").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().authorizeRequests().antMatchers("/course/delete/**").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())


                // widely access REST resource
                .and().authorizeRequests().antMatchers("/api/courses").permitAll()
                .and().authorizeRequests().antMatchers("/api/course/show/**").authenticated()
                .and().authorizeRequests().antMatchers("/api/course/find/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/users").permitAll()
                .and().authorizeRequests().antMatchers("/api/user/find/**").permitAll()

                // authentication REST resource
                .and().authorizeRequests().antMatchers("/api/user/show").authenticated()


                .and().formLogin().defaultSuccessUrl("/", true).permitAll()
                .and().httpBasic()
                .and().csrf().disable()
                .logout().logoutSuccessUrl("/");

    }


    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler (){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }
}
