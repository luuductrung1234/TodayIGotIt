package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.TagTracking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */

public interface TagTrackingSpringData extends CrudRepository<TagTracking, Integer> {
    List<TagTracking> findAllByTagNameContaining(String tagName);
}
