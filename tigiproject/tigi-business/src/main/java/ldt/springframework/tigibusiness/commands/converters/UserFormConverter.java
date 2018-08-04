package ldt.springframework.tigibusiness.commands.converters;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/26/18
 */


import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.domain.User;
import org.springframework.core.convert.converter.Converter;

public interface UserFormConverter extends Converter<UserForm, User> {
    UserForm revert(User source);

    UserForm revertToFewInfo(User source);
}
