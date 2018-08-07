package ldt.springframework.tigirestapi.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import ldt.springframework.tigirestapi.utils.multipart.MultipartFileSender;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/7/18
 */


@RestController
@RequestMapping("/api")
@Api(value = "Course Media API", description = "Operation pertaining to Course Media")
public class MediaRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseService courseService;



    // =======================================
    // =       Non-Auth REST Methods         =
    // =======================================

    @ApiOperation(value = "Show intro video of specific Course")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 403, message = "You don't have right to do  that"),
            @ApiResponse(code = 404, message = "Resource not found"),
    })
    @GetMapping(value="/course/{courseId}/media/video/")
    public void getCourseIntroVideo(@PathVariable("courseId") Integer courseId,
                               HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {

        Course course = courseService.getById(courseId);
        if (course == null) {
            throw new CourseNotFoundException(courseId.toString());
        }

        try {
            URL contentUrl = this.getClass()
                    .getClassLoader().getResource(course.getMediaPath());
            MultipartFileSender.fromPath(Paths.get(contentUrl.toURI()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @ApiOperation(value = "Show intro picture of specific Course")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 403, message = "You don't have right to do  that"),
            @ApiResponse(code = 404, message = "Resource not found"),
    })
    @GetMapping(value="/course/{courseId}/media/image")
    public ResponseEntity<byte[]> getCourseInfoPicture(@PathVariable("courseId") Integer courseId,
                               HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {

        Course course = courseService.getById(courseId);
        if (course == null) {
            throw new CourseNotFoundException(courseId.toString());
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            InputStream in = this.getClass()
                    .getClassLoader().getResourceAsStream(course.getImageUrl());
            byte[] media = IOUtils.toByteArray(in);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
            return responseEntity;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }




    // =======================================
    // =         Auth REST Methods           =
    // =======================================


}
