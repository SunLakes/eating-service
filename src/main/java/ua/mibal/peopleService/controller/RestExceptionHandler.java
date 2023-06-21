package ua.mibal.peopleService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.mibal.peopleService.model.ApiError;

import javax.management.InstanceAlreadyExistsException;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InstanceAlreadyExistsException.class)
    protected ResponseEntity<ApiError> handleInstanceAlreadyExistsException(InstanceAlreadyExistsException e) {
        HttpStatus httpStatus = HttpStatus.ALREADY_REPORTED;
        ResponseEntity<ApiError> response = new ResponseEntity<>(
                new ApiError(httpStatus, e),
                httpStatus
        );
        logger.error("Error " + response);
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseEntity<ApiError> response = new ResponseEntity<>(
                new ApiError(httpStatus, e),
                httpStatus
        );
        logger.error("Error " + response);
        return response;
    }
}
