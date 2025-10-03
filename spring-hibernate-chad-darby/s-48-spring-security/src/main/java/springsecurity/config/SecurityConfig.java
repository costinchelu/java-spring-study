package springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // we will hard-code users for now. Later they will come from a DB
        // (in memory authentication)

        UserBuilder users = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("12345").roles("EMPLOYEE"))
                .withUser(users.username("mary").password("12345").roles("EMPLOYEE", "MANAGER"))
                .withUser(users.username("susan").password("12345").roles("EMPLOYEE", "ADMIN"));

        // spring will provide a default login page
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // reference our custom login form
        // configure security of web paths in application, login, logout etc

        http.authorizeRequests()
                .antMatchers("/").hasRole("EMPLOYEE")
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser").permitAll()
               .and()
                .logout().permitAll()
               .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }
}
