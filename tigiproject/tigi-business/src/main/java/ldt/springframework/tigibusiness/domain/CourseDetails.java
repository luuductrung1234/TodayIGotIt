package ldt.springframework.tigibusiness.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */

@Entity
public class CourseDetails extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer chapter;

    private String title;

    @ManyToOne
    private Course course;

    @OneToMany(
            mappedBy = "courseDetails",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CourseResource> courseResources = new ArrayList<>();


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseDetails() {
    }

    public CourseDetails(Integer chapter, String title) {
        this.chapter = chapter;
        this.title = title;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    @JsonIgnore
    @JsonProperty(value = "courseDetails_course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CourseResource> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(List<CourseResource> courseResources) {
        this.courseResources = courseResources;
    }

    public CourseDetails addCourseResources(CourseResource courseResource){
        courseResource.setCourseDetails(this);
        this.courseResources.add(courseResource);
        return this;
    }

    public CourseDetails removeCourseResources(CourseResource courseResource){
        courseResource.setCourseDetails(null);
        this.courseResources.remove(courseResource);
        return this;
    }
}
