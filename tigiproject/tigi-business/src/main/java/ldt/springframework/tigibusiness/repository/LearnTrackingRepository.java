package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseResource;
import ldt.springframework.tigibusiness.domain.LearnTracking;
import ldt.springframework.tigibusiness.domain.User;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */
public interface LearnTrackingRepository extends CRUDRepository<LearnTracking>{
    LearnTracking findByUserAndCourseResource(User user, CourseResource courseResource);
}
