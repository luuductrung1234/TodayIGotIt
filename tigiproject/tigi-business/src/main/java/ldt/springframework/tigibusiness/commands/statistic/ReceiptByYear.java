package ldt.springframework.tigibusiness.commands.statistic;

import java.math.BigDecimal;
import java.time.Month;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/11/18
 */
public class ReceiptByYear {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private BigDecimal totalAmount;

    private int year;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public ReceiptByYear() {
    }

    public ReceiptByYear(BigDecimal totalAmount, int year) {
        this.totalAmount = totalAmount;
        this.year = year;
    }

    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
