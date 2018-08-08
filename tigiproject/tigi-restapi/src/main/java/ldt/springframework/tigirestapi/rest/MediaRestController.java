package ldt.springframework.tigirestapi.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseResource;
import ldt.springframework.tigibusiness.enums.ResourceType;
import ldt.springframework.tigibusiness.services.CourseResourceService;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import ldt.springframework.tigirestapi.exception.course.CourseResourceNotFoundException;
import ldt.springframework.tigirestapi.exception.course.CourseResourceRequestNotValidException;
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

    @Autowired
    private CourseResourceService courseResourceService;



    // =======================================
    // =       Non-Auth REST Methods         =
    // =======================================

    @ApiOperation(value = "Show intro video of specific Course")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 403, message = "You don't have right to do  that"),
            @ApiResponse(code = 404, message = "Resource not found"),
    })
    @GetMapping(value="/course/{courseId}/media/video")
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

    @ApiOperation(value = "Show video resource of specific Course")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 403, message = "You don't have right to do  that"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 400, message = "Resource not valid for this request"),
    })
    @GetMapping(value="/course/resource/{resourceId}/media/video")
    public void getCourseResourceVideo(@PathVariable("resourceId") Integer resourceId,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response)
            throws Exception {

        CourseResource courseResource = courseResourceService.getById(resourceId);
        if (courseResource == null) {
            throw new CourseResourceNotFoundException();
        }
        if(courseResource.getResourceType() != ResourceType.VIDEO){
            throw new CourseResourceRequestNotValidException();
        }

        try {
            URL contentUrl = this.getClass()
                    .getClassLoader().getResource(courseResource.getResourcePath());
            MultipartFileSender.fromPath(Paths.get(contentUrl.toURI()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @ApiOperation(value = "Show file resource of specific Course")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 403, message = "You don't have right to do  that"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 400, message = "Resource not valid for this request"),
    })
    @GetMapping(value="/course/resource/{resourceId}/media/file")
    public ResponseEntity<byte[]> getCourseResourceFile(@PathVariable("resourceId") Integer resourceId,
                                       HttpServletRequest request,
                                       HttpServletResponse response)
            throws Exception {

        CourseResource courseResource = courseResourceService.getById(resourceId);
        if (courseResource == null) {
            throw new CourseResourceNotFoundException();
        }
        if(courseResource.getResourceType() != ResourceType.FILE){
            throw new CourseResourceRequestNotValidException();
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            InputStream in = this.getClass()
                    .getClassLoader().getResourceAsStream(courseResource.getResourcePath());
            byte[] media = IOUtils.toByteArray(in);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
            return responseEntity;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }
}
