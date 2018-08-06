package ldt.springframework.tigibusiness.commands.converters;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

        user.setRoles(source.getUserRoles());

        if(source.getUserCart() != null){
            for (CartDetails cartDetails:
                 source.getUserCart().getCartDetails()) {
                cartDetails.setCart(source.getUserCart());
            }
            user.setCart(source.getUserCart());
        }else{
            source.setUserCart(new Cart());
            user.setCart(source.getUserCart());
        }

        if(source.getUserOrders() != null){
            for (Order order:
                 source.getUserOrders()) {
                for (OrderDetails orderDetails : order.getOrderDetails()) {
                    orderDetails.setOrder(order);
                }
                user.addOrders(order);
            }
        }else{
            user.setOrders(null);
        }

        if (source.getUserCourseOwners() != null){
            for(CourseOwner courseOwner : source.getUserCourseOwners()){
                user.addCourseOwer(courseOwner);
            }
        }else{
            user.setCourseOwners(null);
        }

        user.setCustomer(new Customer());
        user.getCustomer().setId(source.getCustomerId());
        user.getCustomer().setVersion(source.getCustomerVersion());
        user.getCustomer().setFirstName(source.getFirstName());
        user.getCustomer().setLastName(source.getLastName());
        user.getCustomer().setEmail(source.getEmail());
        user.getCustomer().setPhoneNumber(source.getPhoneNumber());

        user.getCustomer().setBillingAddress(new Address());
        user.getCustomer().getBillingAddress().setAddressLine1(source.getBillingAddressLine1());
        user.getCustomer().getBillingAddress().setAddressLine2(source.getBillingAddressLine2());
        user.getCustomer().getBillingAddress().setCity(source.getBillingCity());
        user.getCustomer().getBillingAddress().setState(source.getBillingState());
        user.getCustomer().getBillingAddress().setZipCode(source.getBillingZipCode());

        user.getCustomer().setShippingAddress(new Address());
        user.getCustomer().getShippingAddress().setAddressLine1(source.getShippingAddressLine1());
        user.getCustomer().getShippingAddress().setAddressLine2(source.getShippingAddressLine2());
        user.getCustomer().getShippingAddress().setCity(source.getShippingCity());
        user.getCustomer().getShippingAddress().setState(source.getShippingState());
        user.getCustomer().getShippingAddress().setZipCode(source.getShippingZipCode());

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
        userForm.setUserRoles(source.getRoles());
        userForm.setUserOrders(source.getOrders());
        userForm.setUserCourseOwners(source.getCourseOwners());

        userForm.setCustomerId(source.getCustomer().getId());
        userForm.setCustomerVersion(source.getCustomer().getVersion());
        userForm.setFirstName(source.getCustomer().getFirstName());
        userForm.setLastName(source.getCustomer().getLastName());
        userForm.setEmail(source.getCustomer().getEmail());
        userForm.setPhoneNumber(source.getCustomer().getPhoneNumber());

        userForm.setBillingAddressLine1(source.getCustomer().getBillingAddress().getAddressLine1());
        userForm.setBillingAddressLine2(source.getCustomer().getBillingAddress().getAddressLine2());
        userForm.setBillingCity(source.getCustomer().getBillingAddress().getCity());
        userForm.setBillingState(source.getCustomer().getBillingAddress().getState());
        userForm.setBillingZipCode(source.getCustomer().getBillingAddress().getZipCode());

        userForm.setShippingAddressLine1(source.getCustomer().getShippingAddress().getAddressLine1());
        userForm.setShippingAddressLine2(source.getCustomer().getShippingAddress().getAddressLine2());
        userForm.setShippingCity(source.getCustomer().getShippingAddress().getCity());
        userForm.setShippingState(source.getCustomer().getShippingAddress().getState());
        userForm.setShippingZipCode(source.getCustomer().getShippingAddress().getZipCode());

        return userForm;
    }


    @Override
    public UserForm revertToFewInfo(User source) {
        UserForm userForm = new UserForm();

        userForm.setUserId(0);
        userForm.setUserVersion(0);
        userForm.setUserName("");
        userForm.setPasswordText("");
        userForm.setPasswordTextConf("");
        userForm.setPasswordEncrypted("");
        userForm.setUserCart(new Cart());
        userForm.setUserRoles(new ArrayList<>());
        userForm.setUserCourseOwners(new ArrayList<>());

        userForm.setCustomerId(source.getCustomer().getId());
        userForm.setCustomerVersion(source.getCustomer().getVersion());
        userForm.setFirstName(source.getCustomer().getFirstName());
        userForm.setLastName(source.getCustomer().getLastName());
        userForm.setEmail(source.getCustomer().getEmail());
        userForm.setPhoneNumber(source.getCustomer().getPhoneNumber());

        userForm.setBillingAddressLine1(source.getCustomer().getBillingAddress().getAddressLine1());
        userForm.setBillingAddressLine2(source.getCustomer().getBillingAddress().getAddressLine2());
        userForm.setBillingCity(source.getCustomer().getBillingAddress().getCity());
        userForm.setBillingState(source.getCustomer().getBillingAddress().getState());
        userForm.setBillingZipCode(source.getCustomer().getBillingAddress().getZipCode());

        userForm.setShippingAddressLine1(source.getCustomer().getShippingAddress().getAddressLine1());
        userForm.setShippingAddressLine2(source.getCustomer().getShippingAddress().getAddressLine2());
        userForm.setShippingCity(source.getCustomer().getShippingAddress().getCity());
        userForm.setShippingState(source.getCustomer().getShippingAddress().getState());
        userForm.setShippingZipCode(source.getCustomer().getShippingAddress().getZipCode());

        return userForm;
    }
}
