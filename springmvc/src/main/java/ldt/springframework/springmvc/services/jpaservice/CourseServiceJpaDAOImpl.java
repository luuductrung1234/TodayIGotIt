package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.springmvc.commands.CourseForm;
import ldt.springframework.springmvc.commands.converters.CourseFormConverter;
import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.repository.CourseRepository;
import ldt.springframework.springmvc.services.CourseService;
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
}
