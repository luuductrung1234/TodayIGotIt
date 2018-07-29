package ldt.springframework.springmvc.repository.springdatarepository.data;

import ldt.springframework.springmvc.domain.Cart;
import org.springframework.data.repository.CrudRepository;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */
public interface CartSpringData extends CrudRepository<Cart, Integer> {
}
