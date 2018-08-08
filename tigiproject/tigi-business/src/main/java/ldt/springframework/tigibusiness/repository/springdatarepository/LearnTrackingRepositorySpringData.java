package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.LearnTracking;
import ldt.springframework.tigibusiness.repository.LearnTrackingRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.LearnTrackingSpringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */

@Repository
public class LearnTrackingRepositorySpringData implements LearnTrackingRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    LearnTrackingSpringData learnTrackingSpringData;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<LearnTracking> learnTrackings = new ArrayList<>();
        learnTrackingSpringData.findAll().forEach(learnTrackings::add);
        return learnTrackings;
    }

    @Override
    public LearnTracking getById(Integer id) {
        if(learnTrackingSpringData.findById(id).isPresent()){
            return learnTrackingSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public LearnTracking saveOrUpdate(LearnTracking learnTracking) {
        return learnTrackingSpringData.save(learnTracking);
    }

    @Override
    public void delete(Integer id) {
        learnTrackingSpringData.deleteById(id);
    }
}
