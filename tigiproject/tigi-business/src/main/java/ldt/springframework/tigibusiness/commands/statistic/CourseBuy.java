package ldt.springframework.tigibusiness.commands.statistic;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/11/18
 */

public class CourseBuy {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String courseName;

    private Integer buyCount;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseBuy() {
    }

    public CourseBuy(String courseName, Integer buyCount) {
        this.courseName = courseName;
        this.buyCount = buyCount;
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

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }
}
