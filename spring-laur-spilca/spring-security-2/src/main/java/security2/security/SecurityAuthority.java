package security2.security;


import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import security2.entities.Authority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
