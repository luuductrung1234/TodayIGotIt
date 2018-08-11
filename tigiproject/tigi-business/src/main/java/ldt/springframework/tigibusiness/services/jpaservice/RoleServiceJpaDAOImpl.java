package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.RoleType;
import ldt.springframework.tigibusiness.repository.RoleRepository;
import ldt.springframework.tigibusiness.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Service
@Profile("jpadao")
public class RoleServiceJpaDAOImpl implements RoleService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private RoleRepository roleRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return roleRepository.listAll();
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.getById(id);
    }

    @Override
    public Role saveOrUpdate(Role role) {
        return roleRepository.saveOrUpdate(role);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(id);
    }

    @Override
    public Role findFirstByType(RoleType type) {
        return roleRepository.findFirstByType(type);
    }
}
