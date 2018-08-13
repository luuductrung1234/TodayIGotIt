package ldt.springframework.tigirestapi.config;

import ldt.springframework.tigibusiness.enums.RoleType;
import ldt.springframework.tigibusiness.security.CustomAuthenticationSuccessHandler;
import ldt.springframework.tigibusiness.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        configWidelyAccessWebResource(http);
        configAuthAccessWebResource(http);
        configWidelyAccessRestResource(http);
        configAuthAccessRestResource(http);
        http
                //.and().formLogin().defaultSuccessUrl("/api/user/show", true).permitAll()
                .logout().logoutUrl("/api/user/logout").logoutSuccessUrl("/").deleteCookies("auth_code", "JSESSIONID")
                .and().httpBasic()
                .and().csrf().disable();
    }




    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler (){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }



    // =======================================
    // =          Business Methods           =
    // =======================================

    private void configWidelyAccessWebResource(HttpSecurity http) throws Exception{
        http
                // static resource
                .authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/css").permitAll()
                .and().authorizeRequests().antMatchers("/js").permitAll()

                .and().authorizeRequests().antMatchers("/customers").permitAll()
                .and().authorizeRequests().antMatchers("/courses").permitAll()
                .and().authorizeRequests().antMatchers("/course/show/**").permitAll();
    }

    private void configAuthAccessWebResource(HttpSecurity http) throws Exception{
        http
                // authentication resources
                .authorizeRequests().antMatchers("/user/**").authenticated()
                .and().authorizeRequests().antMatchers("/customer/**").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/course/new").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().authorizeRequests().antMatchers("/course/edit/**").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().authorizeRequests().antMatchers("/course/delete/**").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name());
    }

    private void configWidelyAccessRestResource(HttpSecurity http) throws  Exception{
        http.cors()
                // Search API
                .and().authorizeRequests().antMatchers("/api/search").permitAll()

                // Course API
                .and().authorizeRequests().antMatchers("/api/courses").permitAll()
                .and().authorizeRequests().antMatchers("/api/courses/page/*/size/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/course/find/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/course/find/*/page/*/size/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/course/info/**").permitAll()
                .and().authorizeRequests().antMatchers("api/course/*/media/image").permitAll()
                .and().authorizeRequests().antMatchers("api/course/*/media/video").permitAll()
                .and().authorizeRequests().antMatchers("api/course/*/review").permitAll()
                .and().authorizeRequests().antMatchers("api/course/*/rate").permitAll()
                .and().authorizeRequests().antMatchers("api/course/*/rate/full").permitAll()
                .and().authorizeRequests().antMatchers("api/course/*/instructor").permitAll()
                .and().authorizeRequests().antMatchers("api/course/tags").permitAll()
                .and().authorizeRequests().antMatchers("api/course/find/tag/**").permitAll()

                // User API
                .and().authorizeRequests().antMatchers("/api/users").permitAll()
                .and().authorizeRequests().antMatchers("/api/users/count").permitAll()
                .and().authorizeRequests().antMatchers("/api/user/find/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/user/new").permitAll();
    }

    private void configAuthAccessRestResource(HttpSecurity http) throws  Exception{
        http.cors()
                // Statistic
                .and().authorizeRequests().antMatchers("/api/statistic/course/buy").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/course/most/buy").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/course/rate").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/course/most/rate").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/receipt/day/**").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/receipt/month/**").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/receipt/year/**").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/instructor/most/buy").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/statistic/student/most/buy").hasAnyAuthority(RoleType.ADMIN.name())

                // Course API
                .and().authorizeRequests().antMatchers("/api/course/new").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.TEACHER.name())
                .and().authorizeRequests().antMatchers("/api/course/*/resources").authenticated()
                .and().authorizeRequests().antMatchers("/api/course/resource/*/media/video").authenticated()

                // User API
                .and().authorizeRequests().antMatchers("/api/users/full").hasAnyAuthority(RoleType.ADMIN.name())
                .and().authorizeRequests().antMatchers("/api/user/info").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/logout").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/info/courses").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/info/cart").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/info/orders").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/info/tracking/course/**").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/update").authenticated()

                // Cart API
                .and().authorizeRequests().antMatchers("/api/user/cart/add/**").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/cart/remove/**").authenticated()


                // Order API
                .and().authorizeRequests().antMatchers("/api/user/order/checkout").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/order/buynow/**").authenticated()
                .and().authorizeRequests().antMatchers("/api/user/order/pay").authenticated();



    }
}
