package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.enums.OwerType;
import ldt.springframework.tigibusiness.repository.CourseOwnerRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.CourseOwnerSpringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/7/18
 */

@Repository
public class CourseOwnerRepositorySpringDataImpl implements CourseOwnerRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseOwnerSpringData courseOwnerSpringData;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<CourseOwner> findAllByCourse(Course course) {
        return courseOwnerSpringData.findAllByCourse(course);
    }

    @Override
    public List<CourseOwner> findAllByCourseAndOwnerType(Course course, OwerType owerType) {
        return courseOwnerSpringData.findAllByCourseAndOwerType(course, owerType);
    }

    @Override
    public List<?> listAll() {
        List<CourseOwner> courseOwners = new ArrayList<>();
        courseOwnerSpringData.findAll().forEach(courseOwners::add);

        return courseOwners;
    }

    @Override
    public CourseOwner getById(Integer id) {
        if(courseOwnerSpringData.findById(id).isPresent()){
            return courseOwnerSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public CourseOwner saveOrUpdate(CourseOwner courseOwner) {
        return courseOwnerSpringData.save(courseOwner);
    }

    @Override
    public void delete(Integer id) {
        courseOwnerSpringData.deleteById(id);
    }
}
