package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/12/18
 */
public interface CoursePaginationSpringData extends PagingAndSortingRepository<Course, Integer> {
    Page<Course> findAllByDescriptionContaining( Pageable pageable, String desc);
}
