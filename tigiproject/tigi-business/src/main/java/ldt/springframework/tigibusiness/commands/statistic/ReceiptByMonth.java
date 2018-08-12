package ldt.springframework.tigibusiness.commands.statistic;

import java.math.BigDecimal;
import java.time.Month;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/11/18
 */
public class ReceiptByMonth {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private BigDecimal totalAmount;

    private Month month;

    private int year;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public ReceiptByMonth() {
    }

    public ReceiptByMonth(BigDecimal totalAmount, Month month, int year) {
        this.totalAmount = totalAmount;
        this.month = month;
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

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
