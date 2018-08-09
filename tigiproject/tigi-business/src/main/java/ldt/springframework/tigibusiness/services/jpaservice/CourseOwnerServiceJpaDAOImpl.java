package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.commands.RateForm;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.domain.User;
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
    public User findInstructor(Course course) {
        List<CourseOwner> courseOwners = courseOwnerRepository.findAllByCourseAndOwnerType(course, OwerType.CREATE);

        return courseOwners.get(0).getUser();
    }

    @Override
    public RateForm getCourseRateFull(Course course) {
        List<CourseOwner> courseOwners = courseOwnerRepository.findAllByCourse(course);
        RateForm rateForm = new RateForm(0,0,0,0,0,0,0,0,0,0,0);

        for (CourseOwner courseOwner:
             courseOwners) {
            if(courseOwner.getRate().equals(0f)){
                rateForm.setUserRate0(rateForm.getUserRate0() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(0.5f)){

                rateForm.setUserRate0_5(rateForm.getUserRate0_5() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(1f)){
                rateForm.setUserRate1(rateForm.getUserRate1() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(1.5f)){
                rateForm.setUserRate1_5(rateForm.getUserRate1_5() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(2f)){
                rateForm.setUserRate2(rateForm.getUserRate2() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(2.5f)){
                rateForm.setUserRate2_5(rateForm.getUserRate2_5() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(3f)){
                rateForm.setUserRate3(rateForm.getUserRate3() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(3.5f)){
                rateForm.setUserRate3_5(rateForm.getUserRate3_5() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(4f)){
                rateForm.setUserRate4(rateForm.getUserRate4() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(4.5f)){
                rateForm.setUserRate4_5(rateForm.getUserRate4_5() + 1);
                continue;
            }
            if(courseOwner.getRate().equals(5f)){
                rateForm.setUserRate5(rateForm.getUserRate5() + 1);
            }
        }
        return rateForm;
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
