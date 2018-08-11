package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.TagTracking;
import ldt.springframework.tigibusiness.repository.TagTrackingRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.TagTrackingSpringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */

@Repository
public class TagTrackingRepositoryImpl implements TagTrackingRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    TagTrackingSpringData tagTrackingSpringData;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<TagTracking> tagTrackings = new ArrayList<>();
        tagTrackingSpringData.findAll().forEach(tagTrackings::add);
        return tagTrackings;
    }

    @Override
    public TagTracking getById(Integer id) {
        if(tagTrackingSpringData.findById(id).isPresent()){
            return tagTrackingSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public TagTracking saveOrUpdate(TagTracking tagTracking) {
        return tagTrackingSpringData.save(tagTracking);
    }

    @Override
    public void delete(Integer id) {
        tagTrackingSpringData.deleteById(id);
    }

    @Override
    public List<TagTracking> findAllByTagNameContaining(String tagName) {
        return tagTrackingSpringData.findAllByTagNameContaining(tagName);
    }

    @Override
    public void updateTrackingCauseBySearch(String tagName) {
        List<TagTracking> tagTrackings = findAllByTagNameContaining(tagName);

        for (TagTracking tagTracking:
             tagTrackings) {
            tagTracking.setSearchCount(tagTracking.getSearchCount() + 1);
            saveOrUpdate(tagTracking);
        }
    }
}
