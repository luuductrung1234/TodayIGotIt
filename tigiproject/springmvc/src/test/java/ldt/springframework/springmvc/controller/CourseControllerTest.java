package ldt.springframework.springmvc.controller;

import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.services.CourseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CourseControllerTest {

    // =======================================
    // =           Injection Point           =
    // =======================================

    // Mockito Mock object
    @Mock
    private CourseService courseService;

    // setups up controller, and injects mock objects into it
    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        // initializes controller and mock object
        // the mock object will be automatically inject into the controller
        MockitoAnnotations.initMocks(this);

        // create a standalone MVC Context to mocking
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }


    // =======================================
    // =            Testing Unit             =
    // =======================================

    @Test
    public void testListCourses() throws Exception {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        courses.add(new Course());

        // specific Mockito interaction,
        // tell stub to return list of courses
        when(courseService.listAll()).thenReturn((List) courses);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("view/course/courses"))
                .andExpect(model().attribute("courses", hasSize(2)));
    }

    @Test
    public void testGetCourseId() throws Exception {
        Integer testId = 1;

        // Tell Mockito stub to return new course for ID 1
        when(courseService.getById(testId)).thenReturn(new Course());

        mockMvc.perform(get("/course/" + testId))
                .andExpect(status().isOk())
                .andExpect(view().name("view/course/course"))
                .andExpect(model().attribute("course", instanceOf(Course.class)));
    }

    @Test
    public void testEditCourse() throws Exception {
        Integer testId = 1;

        // Tell Mockito stub to return new course for ID 1
        when(courseService.getById(testId)).thenReturn(new Course());

        mockMvc.perform(get("/course/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("view/course/courseForm"))
                .andExpect(model().attribute("course", instanceOf(Course.class)));
    }

    @Test
    public void testCreateNewCourse() throws Exception {
        Integer testId = 1;

        // should not call the mock service
        verifyNoMoreInteractions(courseService);

        mockMvc.perform(get("/course/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("view/course/courseForm"))
                .andExpect(model().attribute("course", instanceOf(Course.class)));
    }

    @Test
    public void testSaveOrUpdateCourse() throws Exception {
        // these data below is what you want to see after the Save and Update perform
        Integer id = 1;
        String description = "Test Course";
        BigDecimal price = new BigDecimal("12.00");
        String imageUrl = "example.com";
        Course expectReturnCourse = new Course(id, description, price, imageUrl, "");


        when(courseService.saveOrUpdate(ArgumentMatchers.any(Course.class))).thenReturn(expectReturnCourse);

        mockMvc.perform(post("/course")
                .param("id", id.toString())
                .param("description", description)
                .param("price", price.toString())
                .param("imageUrl", imageUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:course/1"))
                .andExpect(model().attribute("course", instanceOf(Course.class)))
                .andExpect(model().attribute("course", hasProperty("id", is(id))))
                .andExpect(model().attribute("course", hasProperty("description", is(description))))
                .andExpect(model().attribute("course", hasProperty("price", is(price))))
                .andExpect(model().attribute("course", hasProperty("imageUrl", is(imageUrl))));


        // After the CourseService's saveOrUpdate() is called the Mockito framework return an expectReturnObject
        // At this point we use Mockito to capture the return object
        ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).saveOrUpdate(courseCaptor.capture());

        // verify properties of bound object
        Course boundCourse = courseCaptor.getValue();
        assertEquals(id, boundCourse.getId());
        assertEquals(description, boundCourse.getDescription());
        assertEquals(price, boundCourse.getPrice());
        assertEquals(imageUrl, boundCourse.getImageUrl());
    }


    @Test
    public void testDelete()throws Exception{
        Integer id = 1;

        mockMvc.perform(get("/course/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/courses"));

        verify(courseService, times(1)).delete(id);
    }
}