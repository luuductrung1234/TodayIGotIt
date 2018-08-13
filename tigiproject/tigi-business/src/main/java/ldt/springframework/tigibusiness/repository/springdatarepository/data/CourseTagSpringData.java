package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.CourseTag;
import ldt.springframework.tigibusiness.domain.TagTracking;
import ldt.springframework.tigibusiness.enums.TagName;
import org.springframework.data.repository.CrudRepository;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */
public interface CourseTagSpringData extends
        CrudRepository<CourseTag, Integer> {
    Iterable<CourseTag> findAllByTagTracking_TagName(String tagName);
}
