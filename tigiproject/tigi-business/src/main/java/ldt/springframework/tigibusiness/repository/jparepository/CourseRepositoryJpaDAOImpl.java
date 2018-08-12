package ldt.springframework.tigibusiness.repository.jparepository;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.repository.CourseRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
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
public class CourseRepositoryJpaDAOImpl extends AbstractJpaDAORepository
        implements CourseRepository {

    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();
        List<Course> result = em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        em.close();

        return result;
    }

    @Override
    public Course getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Course result = em.find(Course.class, id);
        em.close();

        return result;
    }

    @Override
    public Course saveOrUpdate(Course course) {
        EntityManager em = emf.createEntityManager();

        Course savedCourse = null;
        try {
            savedCourse = this.doInTransaction(em, () -> em.merge(course));
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return savedCourse;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        try {
            this.doInTransaction(em, () -> {
                em.remove(em.find(Course.class, id));
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
    }

    @Override
    public List<Course> findByDesc(String desc) {
        return null;
    }

    @Override
    public List<Course> findByDesc(String desc, PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<?> listAll(PageRequest pageRequest) {
        return null;
    }
}
