package ldt.springframework.tigibusiness.commands.statistic;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/11/18
 */

public class CourseRate {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String courseName;

    private Float rate;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseRate() {
    }

    public CourseRate(String courseName, Float rate) {
        this.courseName = courseName;
        this.rate = rate;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
