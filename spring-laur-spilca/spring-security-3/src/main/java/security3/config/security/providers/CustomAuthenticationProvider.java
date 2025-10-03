package security3.config.security.providers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import security3.config.security.authentication.CustomAuthentication;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${our.very.very.very.secret.key}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        if (key.equals(ca.getKey())) {
            return new CustomAuthentication(true, null);
        }
        throw new BadCredentialsException("Bad credentials!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
