package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.commands.converters.CourseFormConverter;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.repository.CourseRepository;
import ldt.springframework.tigibusiness.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 */

@Service
@Profile(value = "jpadao")
public class CourseServiceJpaDAOImpl implements CourseService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseFormConverter courseFormConverter;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public List<?> listAll() {
        return courseRepository.listAll();
    }

    @Override
    public Course getById(Integer id) {
        return courseRepository.getById(id);
    }

    @Override
    public Course saveOrUpdate(Course course) {
        return courseRepository.saveOrUpdate(course);
    }

    @Override
    public void delete(Integer id) {
        courseRepository.delete(id);
    }

    @Override
    public Course saveOrUpdateCourseForm(CourseForm courseForm) {
        Course newCourse = courseFormConverter.convert(courseForm);

        return saveOrUpdate(newCourse);
    }

    @Override
    public List<Course> findByDesc(String desc) {
        return courseRepository.findByDesc(desc);
    }
}
