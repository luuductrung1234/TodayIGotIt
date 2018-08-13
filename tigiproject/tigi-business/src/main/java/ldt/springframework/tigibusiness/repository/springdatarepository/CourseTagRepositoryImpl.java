package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.CourseTag;
import ldt.springframework.tigibusiness.enums.TagName;
import ldt.springframework.tigibusiness.repository.CourseTagRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.CourseTagSpringData;
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
public class CourseTagRepositoryImpl implements CourseTagRepository {


    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseTagSpringData courseTagSpringData;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<CourseTag> courseTags = new ArrayList<>();
        courseTagSpringData.findAll().forEach(courseTags::add);
        return courseTags;
    }

    @Override
    public CourseTag getById(Integer id) {
        if(courseTagSpringData.findById(id).isPresent()){
            return courseTagSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public CourseTag saveOrUpdate(CourseTag courseTag) {
        return courseTagSpringData.save(courseTag);
    }

    @Override
    public void delete(Integer id) {
        courseTagSpringData.deleteById(id);
    }

    @Override
    public Iterable<CourseTag> findAllByTagTracking_TagName(String tagName) {
        List<CourseTag> courseTags = new ArrayList<>();
        courseTagSpringData.findAllByTagTracking_TagName(tagName).forEach(courseTags::add);
        return courseTags;
    }
}
