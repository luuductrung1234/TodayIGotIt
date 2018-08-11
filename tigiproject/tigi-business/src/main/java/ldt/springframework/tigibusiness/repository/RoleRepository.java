package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.RoleType;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */


public interface RoleRepository extends CRUDRepository<Role>{
    Role findFirstByType(RoleType roleType);
}
