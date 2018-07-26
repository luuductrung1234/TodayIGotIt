package ldt.springframework.springmvc.commands.converters;

import ldt.springframework.springmvc.commands.UserForm;
import ldt.springframework.springmvc.domain.Address;
import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */


@Component
public class UserFormConverterImpl implements UserFormConverter{

    @Override
    public User convert(UserForm source) {
        User user = new User();
        user.setId(source.getUserId());
        user.setVersion(source.getUserVersion());
        user.setUsername(source.getUserName());
        user.setPassword(source.getPasswordText());
        user.setEncryptedPassowrd(source.getPasswordEncrypted());
        user.setCart(source.getUserCart());

        user.setCustomer(new Customer());
        user.getCustomer().setBillingAddress(new Address());
        user.getCustomer().setShippingAddress(new Address());

        user.getCustomer().setId(source.getCustomerId());
        user.getCustomer().setVersion(source.getCustomerVersion());
        user.getCustomer().setFirstName(source.getFirstName());
        user.getCustomer().setLastName(source.getLastName());
        user.getCustomer().setEmail(source.getEmail());
        user.getCustomer().setPhoneNumber(source.getPhoneNumber());
        user.getCustomer().setBillingAddress(source.getBillingAddress());
        user.getCustomer().setShippingAddress(source.getShippingAddress());

        return user;

    }

    @Override
    public UserForm revert(User source) {
        UserForm userForm = new UserForm();

        userForm.setUserId(source.getId());
        userForm.setUserVersion(source.getVersion());
        userForm.setUserName(source.getUsername());
        userForm.setPasswordText("");
        userForm.setPasswordTextConf("");
        userForm.setPasswordEncrypted(source.getEncryptedPassowrd());
        userForm.setUserCart(source.getCart());

        userForm.setCustomerId(source.getCustomer().getId());
        userForm.setCustomerVersion(source.getCustomer().getVersion());
        userForm.setFirstName(source.getCustomer().getFirstName());
        userForm.setLastName(source.getCustomer().getLastName());
        userForm.setEmail(source.getCustomer().getEmail());
        userForm.setPhoneNumber(source.getCustomer().getPhoneNumber());
        userForm.setBillingAddress(source.getCustomer().getBillingAddress());
        userForm.setShippingAddress(source.getCustomer().getShippingAddress());

        return userForm;
    }
}
