package ldt.springframework.tigibusiness.commands.converters;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.domain.Course;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/26/18
 */

@Component
public class CourseFormConverterImpl implements CourseFormConverter {

    @Override
    public Course convert(CourseForm source) {
        Course course = new Course(
            source.getCourseId(),
            source.getDescription(),
            new BigDecimal(source.getPrice()),
            source.getImageUrl(),
            source.getMediaPath()
        );
        course.setVersion(source.getCourseVersion());

        return course;
    }

    @Override
    public CourseForm revert(Course source) {
        CourseForm courseForm = new CourseForm();
        courseForm.setCourseId(source.getId());
        courseForm.setDescription(source.getDescription());
        courseForm.setImageUrl(source.getImageUrl());
        courseForm.setMediaPath(source.getImageUrl());
        courseForm.setPrice(source.getPrice().toString());
        courseForm.setCourseVersion(source.getVersion());

        return courseForm;
    }
}
