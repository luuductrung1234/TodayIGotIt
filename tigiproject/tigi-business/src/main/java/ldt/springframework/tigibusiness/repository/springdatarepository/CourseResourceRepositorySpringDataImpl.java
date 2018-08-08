package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.CourseDetails;
import ldt.springframework.tigibusiness.domain.CourseResource;
import ldt.springframework.tigibusiness.repository.CourseResourceRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.CourseResourceSpringData;
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
public class CourseResourceRepositorySpringDataImpl implements CourseResourceRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    CourseResourceSpringData courseResourceSpringData;


    @Override
    public List<?> listAll() {
        List<CourseResource> courseResources = new ArrayList<>();
        courseResourceSpringData.findAll().forEach(courseResources::add);

        return courseResources;
    }

    @Override
    public CourseResource getById(Integer id) {
        if(courseResourceSpringData.findById(id).isPresent()){
            return courseResourceSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public CourseResource saveOrUpdate(CourseResource courseResource) {
        return courseResourceSpringData.save(courseResource);
    }

    @Override
    public void delete(Integer id) {
        courseResourceSpringData.deleteById(id);
    }

    @Override
    public List<CourseResource> findAllByCourseDetails(CourseDetails courseDetails) {
        return courseResourceSpringData.findAllByCourseDetails(courseDetails);
    }
}
