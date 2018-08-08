package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.enums.OwerType;
import ldt.springframework.tigibusiness.repository.CourseOwnerRepository;
import ldt.springframework.tigibusiness.services.CourseOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/7/18
 */

@Service
@Profile(value = "jpadao")
public class CourseOwnerServiceJpaDAOImpl implements CourseOwnerService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    CourseOwnerRepository courseOwnerRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<CourseOwner> findReviewByCourse(Course course) {

        List<CourseOwner> result = new ArrayList<>();
        for (CourseOwner courseOwner:
             courseOwnerRepository.findAllByCourse(course)) {
            if(courseOwner.getOwerType().equals(OwerType.BUY) && !courseOwner.getReview().isEmpty()){
                result.add(courseOwner);
            }
        }

        return result;
    }

    @Override
    public Float getCourseRateAvg(Course course) {
        Float count = 0f;
        Float avg = 0f;
        for (CourseOwner courseOwner:
                courseOwnerRepository.findAllByCourse(course)) {
            if(courseOwner.getOwerType().equals(OwerType.BUY)){

                count++;
                avg += courseOwner.getRate();
            }
        }

        avg /= count;
        return avg;
    }

    @Override
    public List<?> listAll() {
        return courseOwnerRepository.listAll();
    }

    @Override
    public CourseOwner getById(Integer id) {
        return courseOwnerRepository.getById(id);
    }

    @Override
    public CourseOwner saveOrUpdate(CourseOwner courseOwner) {
        return courseOwnerRepository.saveOrUpdate(courseOwner);
    }

    @Override
    public void delete(Integer id) {
        courseOwnerRepository.delete(id);
    }
}
