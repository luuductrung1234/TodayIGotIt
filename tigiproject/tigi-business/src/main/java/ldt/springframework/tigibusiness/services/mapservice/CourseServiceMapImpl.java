package ldt.springframework.tigibusiness.services.mapservice;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.DomainObject;
import ldt.springframework.tigibusiness.services.CourseService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Course> findByDesc(String desc) {
        return null;
    }

    @Override
    public List<Course> findByDesc(String desc, PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<Course> listAll(PageRequest pageRequest) {
        return null;
    }
}
