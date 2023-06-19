package ua.mibal.peopleService.model;

import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class ApiError {

    private HttpStatus status;

    private String timestamp;

    private String message;

    public ApiError(HttpStatus status, Throwable e) {
        Date date = Calendar.getInstance().getTime();
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
        this.status = status;
        this.message = e.getMessage();
    }

    private ApiError() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiError{");
        sb.append("status=").append(status);
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}