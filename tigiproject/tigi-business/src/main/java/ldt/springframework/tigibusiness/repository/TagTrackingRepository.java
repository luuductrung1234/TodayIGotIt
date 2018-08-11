package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.TagTracking;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */
public interface TagTrackingRepository extends CRUDRepository<TagTracking> {
    List<TagTracking> findAllByTagNameContaining(String tagName);

    void updateTrackingCauseBySearch(String tagName);
}
