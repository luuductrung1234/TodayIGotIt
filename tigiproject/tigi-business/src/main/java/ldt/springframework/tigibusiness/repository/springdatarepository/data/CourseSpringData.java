package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.Course;
import org.springframework.data.repository.CrudRepository;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */
public interface CourseSpringData extends CrudRepository<Course, Integer> {
    Iterable<Course> findByDescriptionIsContaining(String desc);
}
