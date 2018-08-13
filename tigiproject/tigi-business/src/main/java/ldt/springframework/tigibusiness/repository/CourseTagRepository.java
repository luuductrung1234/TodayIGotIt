package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.CourseTag;
import ldt.springframework.tigibusiness.enums.TagName;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */
public interface CourseTagRepository extends CRUDRepository<CourseTag> {
    Iterable<CourseTag> findAllByTagTracking_TagName(String tagName);
}
