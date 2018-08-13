package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.CourseTag;
import ldt.springframework.tigibusiness.enums.TagName;
import ldt.springframework.tigibusiness.repository.CourseTagRepository;
import ldt.springframework.tigibusiness.services.CourseTagService;
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
public class CourseTagServiceImpl implements CourseTagService {


    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseTagRepository courseTagRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return courseTagRepository.listAll();
    }

    @Override
    public CourseTag getById(Integer id) {
        return courseTagRepository.getById(id);
    }

    @Override
    public CourseTag saveOrUpdate(CourseTag courseTag) {
        return courseTagRepository.saveOrUpdate(courseTag);
    }

    @Override
    public void delete(Integer id) {
        courseTagRepository.delete(id);
    }

    @Override
    public Iterable<CourseTag> findAllByTagTracking_TagName(String tagName) {
        return courseTagRepository.findAllByTagTracking_TagName(tagName);
    }
}
