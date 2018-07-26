package ldt.springframework.springmvc.commands.converters;

import ldt.springframework.springmvc.commands.CourseForm;
import ldt.springframework.springmvc.commands.UserForm;
import ldt.springframework.springmvc.domain.Course;
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
