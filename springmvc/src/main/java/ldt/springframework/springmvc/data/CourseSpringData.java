package ldt.springframework.springmvc.data;

import ldt.springframework.springmvc.domain.Course;
import org.springframework.data.repository.CrudRepository;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */
public interface CourseSpringData extends CrudRepository<Course, Integer> {
}
