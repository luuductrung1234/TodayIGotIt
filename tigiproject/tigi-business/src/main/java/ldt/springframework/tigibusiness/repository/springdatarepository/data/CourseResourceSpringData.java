package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.CourseDetails;
import ldt.springframework.tigibusiness.domain.CourseResource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */

public interface CourseResourceSpringData extends CrudRepository<CourseResource, Integer> {
    List<CourseResource> findAllByCourseDetails(CourseDetails courseDetails);
}
