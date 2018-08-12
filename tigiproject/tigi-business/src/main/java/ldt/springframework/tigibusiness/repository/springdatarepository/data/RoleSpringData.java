package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.RoleType;
import org.springframework.data.repository.CrudRepository;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */
public interface RoleSpringData extends CrudRepository<Role, Integer> {

    Role findFirstByType(RoleType roleType);
}
