package fr.ippon.microservices.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olivier.revial on 07/03/2016.
 */
public class JsonError {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm:ss");

    private int status;
    private String date;
    private String message;

    public JsonError(int status, String errorMessage) {
        this.status = status;
        this.message = errorMessage;
        this.date = dateFormat.format(new Date());
    }

    public int getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
