package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.commands.UserForm;
import ldt.springframework.springmvc.domain.Customer;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/16/18
 */

public interface CustomerService extends CRUDService<Customer>{
    Customer saveOrUpdateUserForm(UserForm userForm);
}
