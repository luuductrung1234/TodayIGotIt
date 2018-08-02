package ldt.springframework.springmvc.config;

import ldt.springframework.springmvc.enums.RoleType;
import ldt.springframework.springmvc.services.sercurity.CustomAuthenticationSuccessHandler;
import ldt.springframework.springmvc.services.sercurity.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
                .csrf().ignoringAntMatchers("/H2Console").disable()

                // static resource
                .authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/css").permitAll()
                .and().authorizeRequests().antMatchers("/js").permitAll()

                // widely access resource
                .and().authorizeRequests().antMatchers("/customers").permitAll()
                .and().authorizeRequests().antMatchers("/courses").permitAll()
                .and().authorizeRequests().antMatchers("/api/courses").permitAll()
                .and().authorizeRequests().antMatchers("/course/show/**").permitAll()

                // authentication resources
                .and().authorizeRequests().antMatchers("/user/**").authenticated()
                .and().authorizeRequests().antMatchers("/customer/**").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/course/new").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().authorizeRequests().antMatchers("/course/edit/**").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().authorizeRequests().antMatchers("/course/delete/**").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().formLogin().successHandler(customAuthenticationSuccessHandler).loginPage("/login").permitAll()
                .and().logout().logoutSuccessHandler(customLogoutSuccessHandler).permitAll()
                .and().exceptionHandling().accessDeniedPage("/access_denied");

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
