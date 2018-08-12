package ldt.springframework.tigirestapi.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.commands.SearchResult;
import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.CourseFormConverter;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.OwerType;
import ldt.springframework.tigibusiness.enums.RoleType;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.RoleService;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigibusiness.services.machineLearning.NlpManchineLearningService;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/11/18
 */


@RestController
@RequestMapping("/api")
@Api(value = "Statistic API", description = "Perform Visualization Data")
public class SearchRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private NlpManchineLearningService nlpManchineLearningService;

    @Autowired
    private UserFormConverter userFormConverter;

    @Autowired
    private CourseFormConverter courseFormConverter;

    @Autowired
    private RoleService roleService;


    // =======================================
    // =         Non-Auth REST Method        =
    // =======================================

    @ApiOperation(value = "Search for Instructor and Course", response = SearchResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 404, message = "Resource not found"),
    })
    @PostMapping(value = "/search", produces = "application/json")
    public SearchResult showInstructorForCourse(@RequestParam("filter") String filterSentence) {
        try {
            Role role = roleService.findFirstByType(RoleType.TEACHER);
            List<Course> courses = doFindCourse(filterSentence);
            List<User> instructors = new ArrayList<>();


            // Detect Organization
            List<String> orgs = nlpManchineLearningService.detectOrganization(filterSentence);
            if (!orgs.isEmpty()) {
                for (String org : orgs) {
                    // TODO : search course
                    courses = mergeCourses(courses, doFindCourse(org));
                }
            }

            // Detect Instructor
            List<String> persons = nlpManchineLearningService.detectPerson(filterSentence);
            if (!persons.isEmpty()) {
                for (String person : persons) {
                    // TODO : search instructor
                    instructors = mergeIns(instructors, doFindIns(person, role));
                }
            }

            List<String> verbs = nlpManchineLearningService.detectPartOfSpeechByTag(filterSentence, "VB");
            if (!verbs.isEmpty()) {
                for (String verb : verbs) {
                    // TODO : search course
                    courses = mergeCourses(courses, doFindCourse(verb));
                }
            }

            List<String> proNouns = nlpManchineLearningService.detectPartOfSpeechByTag(filterSentence, "NNP");
            if (!proNouns.isEmpty()) {
                for (String proNoun : proNouns) {
                    // TODO : search course
                    courses = mergeCourses(courses, doFindCourse(proNoun));

                    // TODO : search instructor
                    instructors = mergeIns(instructors, doFindIns(proNoun,role));
                }
            }

            List<String> nouns = nlpManchineLearningService.detectPartOfSpeechByTag(filterSentence, "NN");
            if (!nouns.isEmpty()) {
                for (String noun : nouns) {
                    // TODO : search course
                    courses = mergeCourses(courses, doFindCourse(noun));

                    // TODO : search instructor
                    instructors = mergeIns(instructors, doFindIns(noun,role));
                }
            }

            List<String> phrases = nlpManchineLearningService.detectNounPhraseChunk(filterSentence);
            if (!phrases.isEmpty()) {
                for (String phrase : phrases) {
                    // TODO : search course
                    courses = mergeCourses(courses, doFindCourse(phrase));
                }
            }


            // TODO : Merge Instuctor's Course to Courses result
            for (User instructor : instructors) {
                if (instructor.getRoles().contains(role)) {
                    for (CourseOwner courseOwner : instructor.getCourseOwners().subList(0, 4)) {
                        if (!courses.contains(courseOwner.getCourse())) {
                            courses.add(courseOwner.getCourse());
                        }
                    }
                }
            }


            // TODO : Rating


            List<UserForm> insts = new ArrayList<>();
            for (User inst :
                    instructors) {
                insts.add(userFormConverter.revertToFewInfo(inst));
            }

            List<CourseForm> crs = new ArrayList<>();
            for (Course course :
                    courses) {
                crs.add(courseFormConverter.revert(course));
            }


            return new SearchResult(crs, insts);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CourseNotFoundException("");
        }
    }


    private List<Course> doFindCourse(String filter) {
        return courseService.findByDesc(filter);
    }

    private List<User> doFindIns(String filter, Role role) {
        return userService.findAllByCustomerFirstNameOrCustomerLastNameAndRolesContaining(filter, filter, role);
    }

    private List<Course> mergeCourses(List<Course> currentList, List<Course> newList) {
        for (Course course : newList) {
            if (!currentList.contains(course)) {
                currentList.add(course);
            }
        }
        return currentList;
    }

    private List<User> mergeIns(List<User> currentList, List<User> newList) {
        for (User instructor : newList) {
            if (!currentList.contains(instructor)) {
                currentList.add(instructor);
            }
        }
        return currentList;
    }
}
