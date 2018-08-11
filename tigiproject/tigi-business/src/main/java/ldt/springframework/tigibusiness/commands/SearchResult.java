package ldt.springframework.tigibusiness.commands;

import ldt.springframework.tigibusiness.domain.Course;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/11/18
 */


public class SearchResult {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private List<CourseForm> courseList;

    private List<UserForm> instructorList;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public SearchResult(){

    }

    public SearchResult(List<CourseForm> courseList, List<UserForm> instructorList){
        this.instructorList = instructorList;
        this.courseList = courseList;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public List<CourseForm> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseForm> courseList) {
        this.courseList = courseList;
    }

    public List<UserForm> getInstructorList() {
        return instructorList;
    }

    public void setInstructorList(List<UserForm> instructorList) {
        this.instructorList = instructorList;
    }
}
