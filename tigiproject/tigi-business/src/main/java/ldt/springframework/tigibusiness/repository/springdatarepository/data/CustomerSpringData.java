package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */
public interface CustomerSpringData extends CrudRepository<Customer, Integer> {
    List<Customer> findAllByFirstNameOrLastName(String firstName, String lastName);
}
