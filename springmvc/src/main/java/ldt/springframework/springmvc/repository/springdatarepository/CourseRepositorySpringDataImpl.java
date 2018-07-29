package ldt.springframework.springmvc.repository.springdatarepository;

import ldt.springframework.springmvc.repository.springdatarepository.data.CourseSpringData;
import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.repository.CourseRepository;
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
public class CourseRepositorySpringDataImpl implements CourseRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseSpringData courseSpringData;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<Course> courses = new ArrayList<>();
        courseSpringData.findAll().forEach(courses::add);

        return courses;
    }

    @Override
    public Course getById(Integer id) {
        if(courseSpringData.findById(id).isPresent()){
            return courseSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public Course saveOrUpdate(Course course) {
        return courseSpringData.save(course);
    }

    @Override
    public void delete(Integer id) {
        courseSpringData.deleteById(id);
    }
}
