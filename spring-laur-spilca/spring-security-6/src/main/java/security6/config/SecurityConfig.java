package security6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {
    var uds = new InMemoryUserDetailsManager();

    var u1 = User.withUsername("bill")
        .password(passwordEncoder().encode("12345"))
        .authorities("a1")  // --> GrantedAuthority interface
        .build();

    var u2 = User.withUsername("john")
        .password(passwordEncoder().encode("12345"))
        .authorities("a2", "a3")
        .build();

    uds.createUser(u1);
    uds.createUser(u2);

    return uds;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.httpBasic()
        .and()
        .authorizeRequests()
//        .anyRequest().authenticated()
            .mvcMatchers(HttpMethod.GET,"/demo/**").hasAuthority("a1")   //using ANT expressions
            .mvcMatchers("/test/test1").authenticated()
//        .regexMatchers("regex").authenticated()
        .anyRequest().permitAll()
        .and().csrf().disable()     // DON'T DO THIS IN REAL-WORLD APPS
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
/*
ANT expressions:

+-----------------+---------------------------------------------------------+
| Wildcard        |            Description                                  |
+-----------------+---------------------------------------------------------+
| ?               | Matches exactly one character.                          |
| *               | Matches zero or more characters.                        |
| **              | Matches zero or more 'directories' in a path            |
| {spring:[a-z]+} | Matches regExp [a-z]+ as a path variable named "spring" |
+-----------------+---------------------------------------------------------+

Example:                                                                          */
  // /demo/anything/*/something   ---> /demo/anything/abc/something
  //                                   /demo/anything/xyz/something


