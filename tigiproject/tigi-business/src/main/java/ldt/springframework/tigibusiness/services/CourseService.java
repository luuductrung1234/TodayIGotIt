package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.User;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */

public interface CourseService extends CRUDService<Course>{
    Course saveOrUpdateCourseForm(CourseForm courseForm);

    List<Course> findByDesc(String desc);

    List<Course> findByDesc(String desc, PageRequest pageRequest);

    List<Course> listAll(PageRequest pageRequest);
}
