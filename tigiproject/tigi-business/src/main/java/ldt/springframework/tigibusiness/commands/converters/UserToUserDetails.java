package ldt.springframework.tigibusiness.commands.converters;

import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.security.UserDetailsImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/29/18
 */

@Component
public class UserToUserDetails implements Converter<User, UserDetailsImpl> {

    @Override
    public UserDetailsImpl convert(User source) {
        if(source == null)
            return null;
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(source.getUsername());
        userDetails.setPassword(source.getEncryptedPassowrd());
        userDetails.setEnabled(source.getEnabled());

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        source.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getType().name()));
        });

        userDetails.setAuthorities(authorities);

        return userDetails;
    }
}
