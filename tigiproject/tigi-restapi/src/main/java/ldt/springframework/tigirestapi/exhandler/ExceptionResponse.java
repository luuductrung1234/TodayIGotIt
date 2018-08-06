package ldt.springframework.tigirestapi.exhandler;

import java.util.Date;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */
public class ExceptionResponse {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Date timestamp;

    private String message;

    private String details;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public ExceptionResponse(){

    }

    public ExceptionResponse(Date timestamp, String message, String details){
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
