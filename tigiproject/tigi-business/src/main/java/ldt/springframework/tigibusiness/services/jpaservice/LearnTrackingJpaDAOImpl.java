package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.repository.LearnTrackingRepository;
import ldt.springframework.tigibusiness.services.LearnTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */

@Service
public class LearnTrackingJpaDAOImpl implements LearnTrackingService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    LearnTrackingRepository learnTrackingRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return learnTrackingRepository.listAll();
    }

    @Override
    public LearnTracking getById(Integer id) {
        return learnTrackingRepository.getById(id);
    }

    @Override
    public LearnTracking saveOrUpdate(LearnTracking learnTracking) {
        return learnTrackingRepository.saveOrUpdate(learnTracking);
    }

    @Override
    public void delete(Integer id) {
        learnTrackingRepository.delete(id);
    }

    @Override
    public LearnTracking findByUserAndCourseResource(User user, CourseResource courseResource) {
        return learnTrackingRepository.findByUserAndCourseResource(user, courseResource);
    }

    @Override
    public List<LearnTracking> findAllByUserAndCourse(User user, Course course) {
        List<LearnTracking> learnTrackings = new ArrayList<>();
        for (CourseDetails courseDetails:
             course.getCourseDetails()) {
            for (CourseResource courseResource:
                 courseDetails.getCourseResources()) {
                learnTrackings.add(learnTrackingRepository.findByUserAndCourseResource(user, courseResource));
            }
        }
        return learnTrackings;
    }
}
