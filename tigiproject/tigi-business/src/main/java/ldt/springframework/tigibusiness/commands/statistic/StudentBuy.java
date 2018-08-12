package ldt.springframework.tigibusiness.commands.statistic;

import ldt.springframework.tigibusiness.commands.UserForm;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/12/18
 */
public class StudentBuy {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer totalBuy;

    private UserForm student;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public StudentBuy() {
    }

    public StudentBuy(Integer totalBuy, UserForm student) {
        this.totalBuy = totalBuy;
        this.student = student;
    }

    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Integer getTotalBuy() {
        return totalBuy;
    }

    public void setTotalBuy(Integer totalBuy) {
        this.totalBuy = totalBuy;
    }

    public UserForm getStudent() {
        return student;
    }

    public void setStudent(UserForm student) {
        this.student = student;
    }
}
