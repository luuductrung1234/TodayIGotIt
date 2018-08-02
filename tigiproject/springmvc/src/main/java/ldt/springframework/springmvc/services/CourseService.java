package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.commands.CourseForm;
import ldt.springframework.springmvc.domain.Course;

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
