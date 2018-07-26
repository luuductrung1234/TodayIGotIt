package ldt.springframework.springmvc.commands.converters;

import ldt.springframework.springmvc.commands.CourseForm;
import ldt.springframework.springmvc.domain.Course;
import org.springframework.stereotype.Component;

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
            source.getPrice(),
            source.getImageUrl()
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
        courseForm.setPrice(source.getPrice());
        courseForm.setCourseVersion(source.getVersion());

        return courseForm;
    }
}
