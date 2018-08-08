package ldt.springframework.tigibusiness.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */

@Entity
public class LearnTracking extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    @ManyToOne
    private User user;

    @OneToOne
    private CourseResource courseResource;

    private Boolean isCompleted;

    private Long curWatchingSec;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public LearnTracking(){
    }

    public LearnTracking(Boolean isCompleted, Long curWatchingSec, CourseResource courseResource){
        this.isCompleted = isCompleted;
        this.curWatchingSec = curWatchingSec;
        this.courseResource = courseResource;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getCurWatchingSec() {
        return curWatchingSec;
    }

    public void setCurWatchingSec(Long curWatchingSec) {
        this.curWatchingSec = curWatchingSec;
    }

    public CourseResource getCourseResource() {
        return courseResource;
    }

    public void setCourseResource(CourseResource courseResource) {
        this.courseResource = courseResource;
    }
}
