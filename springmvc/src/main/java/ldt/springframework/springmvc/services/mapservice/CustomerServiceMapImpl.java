package ldt.springframework.springmvc.services.mapservice;

import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.domain.DomainObject;
import ldt.springframework.springmvc.services.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/16/18
 */

@Service
@Profile("map")
public class CustomerServiceMapImpl extends AbstractMapService implements CustomerService {

    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CustomerServiceMapImpl(){
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public List<DomainObject> listAll(){
        return super.listAll();
    }

    @Override
    public Customer getById(Integer id){
        return (Customer) super.getById(id);
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        return (Customer) super.saveOrUpdateDomainObject(customer);
    }

    @Override
    public void delete(Integer id) {
        super.deleteDomainObject(id);
    }
}
