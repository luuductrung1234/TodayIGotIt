package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.domain.Customer;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/16/18
 */

public interface CustomerService extends CRUDService<Customer>{
    Customer saveOrUpdateUserForm(UserForm userForm);
}
