package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.TagTracking;
import ldt.springframework.tigibusiness.repository.TagTrackingRepository;
import ldt.springframework.tigibusiness.services.TagTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */

@Service
public class TagTrackingServiceImpl implements TagTrackingService {

    // =======================================
    // =           Injection Point           =
    // =======================================
    @Autowired
    private TagTrackingRepository tagTrackingRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return tagTrackingRepository.listAll();
    }

    @Override
    public TagTracking getById(Integer id) {
        return tagTrackingRepository.getById(id);
    }

    @Override
    public TagTracking saveOrUpdate(TagTracking tagTracking) {
        return tagTrackingRepository.saveOrUpdate(tagTracking);
    }

    @Override
    public void delete(Integer id) {
        tagTrackingRepository.delete(id);
    }

    @Override
    public void updateTrackingCauseBySearch(String tagName) {
        tagTrackingRepository.updateTrackingCauseBySearch(tagName);
    }
}
