package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.repository.CourseRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.CourseSpringData;
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
    public List<Course> findByDesc(String desc) {
        List<Course> courses = new ArrayList<>();
        courseSpringData.findByDescriptionIsContaining(desc).forEach(courses::add);

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
