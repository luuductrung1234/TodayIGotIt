package ldt.springframework.tigibusiness.commands;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/9/18
 */
public class RateForm {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer userRate0;
    private Integer userRate0_5;
    private Integer userRate1;
    private Integer userRate1_5;
    private Integer userRate2;
    private Integer userRate2_5;
    private Integer userRate3;
    private Integer userRate3_5;
    private Integer userRate4;
    private Integer userRate4_5;
    private Integer userRate5;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public RateForm(Integer userRate0, Integer userRate0_5, Integer userRate1, Integer userRate1_5, Integer userRate2, Integer userRate2_5, Integer userRate3, Integer userRate3_5, Integer userRate4, Integer userRate4_5, Integer userRate5) {
        this.userRate0 = userRate0;
        this.userRate0_5 = userRate0_5;
        this.userRate1 = userRate1;
        this.userRate1_5 = userRate1_5;
        this.userRate2 = userRate2;
        this.userRate2_5 = userRate2_5;
        this.userRate3 = userRate3;
        this.userRate3_5 = userRate3_5;
        this.userRate4 = userRate4;
        this.userRate4_5 = userRate4_5;
        this.userRate5 = userRate5;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Integer getUserRate0() {
        return userRate0;
    }

    public void setUserRate0(Integer userRate0) {
        this.userRate0 = userRate0;
    }

    public Integer getUserRate0_5() {
        return userRate0_5;
    }

    public void setUserRate0_5(Integer userRate0_5) {
        this.userRate0_5 = userRate0_5;
    }

    public Integer getUserRate1() {
        return userRate1;
    }

    public void setUserRate1(Integer userRate1) {
        this.userRate1 = userRate1;
    }

    public Integer getUserRate1_5() {
        return userRate1_5;
    }

    public void setUserRate1_5(Integer userRate1_5) {
        this.userRate1_5 = userRate1_5;
    }

    public Integer getUserRate2() {
        return userRate2;
    }

    public void setUserRate2(Integer userRate2) {
        this.userRate2 = userRate2;
    }

    public Integer getUserRate2_5() {
        return userRate2_5;
    }

    public void setUserRate2_5(Integer userRate2_5) {
        this.userRate2_5 = userRate2_5;
    }

    public Integer getUserRate3() {
        return userRate3;
    }

    public void setUserRate3(Integer userRate3) {
        this.userRate3 = userRate3;
    }

    public Integer getUserRate3_5() {
        return userRate3_5;
    }

    public void setUserRate3_5(Integer userRate3_5) {
        this.userRate3_5 = userRate3_5;
    }

    public Integer getUserRate4() {
        return userRate4;
    }

    public void setUserRate4(Integer userRate4) {
        this.userRate4 = userRate4;
    }

    public Integer getUserRate4_5() {
        return userRate4_5;
    }

    public void setUserRate4_5(Integer userRate4_5) {
        this.userRate4_5 = userRate4_5;
    }

    public Integer getUserRate5() {
        return userRate5;
    }

    public void setUserRate5(Integer userRate5) {
        this.userRate5 = userRate5;
    }
}
