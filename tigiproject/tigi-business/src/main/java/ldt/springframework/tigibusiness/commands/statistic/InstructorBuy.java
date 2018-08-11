package ldt.springframework.tigibusiness.commands.statistic;

import ldt.springframework.tigibusiness.commands.UserForm;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/12/18
 */
public class InstructorBuy {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer totalBuy;

    private UserForm instructor;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public InstructorBuy() {
    }

    public InstructorBuy(Integer totalBuy, UserForm instructor) {
        this.totalBuy = totalBuy;
        this.instructor = instructor;
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

    public UserForm getInstructor() {
        return instructor;
    }

    public void setInstructor(UserForm instructor) {
        this.instructor = instructor;
    }
}
