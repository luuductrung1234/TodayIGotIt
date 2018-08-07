package ldt.springframework.tigibusiness.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ldt.springframework.tigibusiness.enums.OwerType;

import javax.persistence.*;

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

    private Float rate;

    private String review;

    private String responsedReview;



    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseOwner(){
    }

    public CourseOwner(OwerType owerType, Course course,
                       Float rate, String review,
                       String responsedReview){
        this.owerType = owerType;
        this.course = course;
        this.review = review;
        this.rate = rate;
        this.responsedReview = responsedReview;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @JsonIgnore
    @JsonProperty(value = "courseOwner_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    @JsonProperty(value = "courseOwner_course")
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getResponsedReview() {
        return responsedReview;
    }

    public void setResponsedReview(String responsedReview) {
        this.responsedReview = responsedReview;
    }
}
