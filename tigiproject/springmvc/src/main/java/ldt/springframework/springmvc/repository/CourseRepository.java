package ldt.springframework.springmvc.repository;

import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.services.CRUDService;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */


public interface CourseRepository extends CRUDService<Course>{
    List<Course> findByDesc(String desc);
}
