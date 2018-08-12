package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.services.CRUDService;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */


public interface CourseRepository extends CRUDService<Course>{
    List<Course> findByDesc(String desc);

    List<Course> findByDesc(String desc, PageRequest pageRequest);

    List<?> listAll(PageRequest pageRequest);
}
