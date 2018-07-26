package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.springmvc.commands.UserForm;
import ldt.springframework.springmvc.commands.converters.UserFormConverter;
import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.repository.CustomerRepository;
import ldt.springframework.springmvc.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 */

@Service
@Profile("jpadao")
public class CustomerServiceJpaDAOImpl implements CustomerService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserFormConverter userFormConverter;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public List<?> listAll() {
        return customerRepository.listAll();
    }

    @Override
    public Customer getById(Integer id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer saveOrUpdate(Customer customer){
        return customerRepository.saveOrUpdate(customer);
    }

    @Override
    public void delete(Integer id) {
        customerRepository.delete(id);
    }

    @Override
    public Customer saveOrUpdateUserForm(UserForm userForm) {
        User newUser = userFormConverter.convert(userForm);

        return saveOrUpdate(newUser.getCustomer());
    }
}
