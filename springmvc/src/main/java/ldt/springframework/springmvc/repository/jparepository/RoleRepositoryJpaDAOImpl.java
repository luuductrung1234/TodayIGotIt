package ldt.springframework.springmvc.repository.jparepository;

import ldt.springframework.springmvc.domain.security.Role;
import ldt.springframework.springmvc.repository.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Repository
@Profile("jpadao-pure")
public class RoleRepositoryJpaDAOImpl extends AbstractJpaDAORepository
        implements RoleRepository {

    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();
        List<Role> result = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        em.close();

        return result;
    }

    @Override
    public Role getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Role result = em.find(Role.class, id);
        em.close();

        return result;
    }

    @Override
    public Role saveOrUpdate(Role role) {
        EntityManager em = emf.createEntityManager();

        Role savedRole = null;
        try {
            savedRole = this.doInTransaction(em, () -> em.merge(role));
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return savedRole;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        try {
            this.doInTransaction(em, () -> {
                em.remove(em.find(Role.class, id));
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
    }
}
