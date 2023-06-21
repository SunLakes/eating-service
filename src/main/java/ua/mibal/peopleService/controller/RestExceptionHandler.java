/*
 * Copyright (c) 2023. http://t.me/mibal_ua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        logger.warn("Error " + response);
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseEntity<ApiError> response = new ResponseEntity<>(
                new ApiError(httpStatus, e),
                httpStatus
        );
        logger.warn("Error " + response);
        return response;
    }
}
