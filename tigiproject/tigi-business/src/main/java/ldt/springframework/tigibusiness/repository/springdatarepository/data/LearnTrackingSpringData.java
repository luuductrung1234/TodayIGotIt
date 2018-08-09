package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.CourseResource;
import ldt.springframework.tigibusiness.domain.LearnTracking;
import ldt.springframework.tigibusiness.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */
public interface LearnTrackingSpringData extends CrudRepository<LearnTracking, Integer> {
    List<LearnTracking> findByUserAndCourseResource(User user, CourseResource courseResource);
}
