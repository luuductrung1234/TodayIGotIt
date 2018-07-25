package ldt.springframework.springmvc.repository.springdatarepository;

import ldt.springframework.springmvc.data.RoleSpringData;
import ldt.springframework.springmvc.domain.security.Role;
import ldt.springframework.springmvc.repository.RoleRepository;
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
public class RoleRepositorySpringDataImpl implements RoleRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private RoleSpringData roleSpringData;

    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<Role> orders = new ArrayList<>();
        roleSpringData.findAll().forEach(orders::add);

        return orders;
    }

    @Override
    public Role getById(Integer id) {
        if(roleSpringData.findById(id).isPresent()){
            return roleSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public Role saveOrUpdate(Role role) {
        return roleSpringData.save(role);
    }

    @Override
    public void delete(Integer id) {
        roleSpringData.deleteById(id);
    }
}
