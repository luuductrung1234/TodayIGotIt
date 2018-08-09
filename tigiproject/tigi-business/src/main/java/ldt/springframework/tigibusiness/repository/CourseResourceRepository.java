package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.CourseDetails;
import ldt.springframework.tigibusiness.domain.CourseResource;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */
public interface CourseResourceRepository extends CRUDRepository<CourseResource> {
    List<CourseResource> findAllByCourseDetails(CourseDetails courseDetails);
}