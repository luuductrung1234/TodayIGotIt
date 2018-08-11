package ldt.springframework.tigibusiness.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */

@Entity
public class CourseTag extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    @ManyToOne
    private Course course;

    @OneToOne
    private TagTracking tagTracking;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseTag(){

    }

    public CourseTag(TagTracking tagTracking){
        this.tagTracking = tagTracking;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @JsonIgnore
    @JsonProperty(value = "courseTag_course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TagTracking getTagTracking() {
        return tagTracking;
    }

    public void setTagTracking(TagTracking tagTracking) {
        this.tagTracking = tagTracking;
    }
}
