package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.domain.TagTracking;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */
public interface TagTrackingService extends CRUDService<TagTracking> {
    void updateTrackingCauseBySearch(String tagName);
}
