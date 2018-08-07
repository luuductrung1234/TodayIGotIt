package ldt.springframework.tigirestapi.rest;


import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import ldt.springframework.tigirestapi.utils.multipart.MultipartFileSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.file.Paths;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/7/18
 */


@RestController
@RequestMapping("/api")
public class MediaRestController {
    @Autowired
    private CourseService courseService;

    @GetMapping(value="/course/media/{id}")
    public void getEpisodeFile(@PathVariable("id") Integer courseId,
                               HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {

        Course course = courseService.getById(courseId);

        try {
            if (course != null) {
                MultipartFileSender.fromPath(Paths.get(course.getMediaPath()))
                        .with(request)
                        .with(response)
                        .serveResource();
            } else {
                throw new CourseNotFoundException(courseId.toString());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
