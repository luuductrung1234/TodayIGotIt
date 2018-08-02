package ldt.springframework.springmvc.repository.springdatarepository;

import ldt.springframework.springmvc.repository.springdatarepository.data.CustomerSpringData;
import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.repository.CustomerRepository;
import ldt.springframework.springmvc.sercurity.encrypt.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Repository
@Profile(value = {"springdatajpa", "jpadao"})
public class CustomerRepositorySpringDataImpl implements CustomerRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CustomerSpringData customerSpringData;

    @Autowired
    private EncryptionService encryptionService;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<Customer> customers = new ArrayList<>();
        customerSpringData.findAll().forEach(customers::add);

        return customers;
    }

    @Override
    public Customer getById(Integer id) {
        if(customerSpringData.findById(id).isPresent()){
            return customerSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        if(customer.getUser() != null && customer.getUser().getPassword() != null){
            customer.getUser().setEncryptedPassowrd(
                    encryptionService.encryptString(customer.getUser().getPassword()));
        }
        return customerSpringData.save(customer);
    }

    @Override
    public void delete(Integer id) {
        customerSpringData.deleteById(id);
    }
}
