package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.User;

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
}
