package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.User;
import org.springframework.data.repository.CrudRepository;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */
public interface UserSpringData extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
