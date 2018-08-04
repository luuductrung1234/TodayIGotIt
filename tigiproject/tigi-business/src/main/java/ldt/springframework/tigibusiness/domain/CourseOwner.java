package ldt.springframework.tigibusiness.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ldt.springframework.tigibusiness.enums.OwerType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/4/18
 */

@Entity
public class CourseOwner extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private OwerType owerType;

    @ManyToOne
    private User user;

    @OneToOne
    private Course course;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseOwner(){
    }

    public CourseOwner(OwerType owerType, Course course){
        this.owerType = owerType;
        this.course = course;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @JsonIgnore
    @JsonProperty(value = "courseOwer_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public OwerType getOwerType() {
        return owerType;
    }

    public void setOwerType(OwerType owerType) {
        this.owerType = owerType;
    }
}
