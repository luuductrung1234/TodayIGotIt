package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.enums.OwerType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/7/18
 */
public interface CourseOwnerSpringData extends CrudRepository<CourseOwner, Integer> {
    List<CourseOwner> findAllByCourse(Course course);

    List<CourseOwner> findAllByCourseAndOwerType(Course course, OwerType ownerType);
}
