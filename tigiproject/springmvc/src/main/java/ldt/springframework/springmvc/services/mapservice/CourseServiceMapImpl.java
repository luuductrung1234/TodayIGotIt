package ldt.springframework.springmvc.services.mapservice;

import ldt.springframework.springmvc.commands.CourseForm;
import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.domain.DomainObject;
import ldt.springframework.springmvc.services.CourseService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */

@Service
@Profile("map")
public class CourseServiceMapImpl extends AbstractMapService implements CourseService {

    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseServiceMapImpl() {
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Course getById(Integer id){
        return (Course) super.getById(id);
    }

    @Override
    public Course saveOrUpdate(Course course){
        return (Course) super.saveOrUpdateDomainObject(course);
    }

    @Override
    public void delete(Integer id){
        super.deleteDomainObject(id);
    }

    @Override
    public Course saveOrUpdateCourseForm(CourseForm courseForm) {
        return null;
    }
}
