package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.CourseDetails;
import ldt.springframework.tigibusiness.domain.CourseResource;
import ldt.springframework.tigibusiness.repository.CourseResourceRepository;
import ldt.springframework.tigibusiness.services.CourseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */

@Service
public class CourseResourceJpaDAOImpl implements CourseResourceService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseResourceRepository courseResourceRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<CourseResource> findAllByCourseDetails(CourseDetails courseDetails) {
        return courseResourceRepository.findAllByCourseDetails(courseDetails);
    }

    @Override
    public List<?> listAll() {
        return courseResourceRepository.listAll();
    }

    @Override
    public CourseResource getById(Integer id) {
        return courseResourceRepository.getById(id);
    }

    @Override
    public CourseResource saveOrUpdate(CourseResource courseResource) {
        return courseResourceRepository.saveOrUpdate(courseResource);
    }

    @Override
    public void delete(Integer id) {
        courseResourceRepository.delete(id);
    }
}
