package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.domain.CourseTag;
import ldt.springframework.tigibusiness.enums.TagName;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */
public interface CourseTagService extends CRUDService<CourseTag>{
    Iterable<CourseTag> findAllByTagTracking_TagName(String tagName);
}
