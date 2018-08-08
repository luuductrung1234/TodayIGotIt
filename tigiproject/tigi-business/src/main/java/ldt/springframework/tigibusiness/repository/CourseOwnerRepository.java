package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.services.CRUDService;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/7/18
 */
public interface CourseOwnerRepository extends CRUDService<CourseOwner> {
    List<CourseOwner> findAllByCourse(Course course);
}
