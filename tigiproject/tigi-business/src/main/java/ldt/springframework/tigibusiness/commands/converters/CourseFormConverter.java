package ldt.springframework.tigibusiness.commands.converters;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.domain.Course;
import org.springframework.core.convert.converter.Converter;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/26/18
 */
public interface CourseFormConverter extends Converter<CourseForm, Course> {
    CourseForm revert(Course source);
}
