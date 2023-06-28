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

package ua.mibal.eatingService.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.mibal.eatingService.controller.GlobalExceptionHandler;
import ua.mibal.eatingService.dao.EatingDao;
import ua.mibal.eatingService.dao.PersonDao;
import ua.mibal.eatingService.model.ApiError;
import ua.mibal.eatingService.model.Entry;
import ua.mibal.eatingService.model.Person;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
@Aspect
public class LoggingAspect {

    private final PersonDao personDao;

    private final static Logger exceptionHandlerLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final static Logger eatingDaoLogger = LoggerFactory.getLogger(EatingDao.class);

    public LoggingAspect(PersonDao personDao) {
        this.personDao = personDao;
    }

    @AfterReturning(
            pointcut = "execution(* ua.mibal.eatingService.controller.GlobalExceptionHandler.handleMethodArgumentNotValid(..))",
            returning = "error"
    )
    void afterValidationExceptionHandlingAdvice(ResponseEntity<Object> error) {
        exceptionHandlerLogger.warn(
                "Handled errors: " + ((ApiError) error.getBody()).getMessage()
        );
    }

    @AfterReturning(
            pointcut = "execution(* ua.mibal.eatingService.dao.EatingDao.save(..))",
            returning = "entry"
    )
    void afterAddingEntryAdvice(Entry entry) {
        Person person = personDao.getByBraceletId(entry.getBraceletId())
                .orElse(Person.emptyPerson);
        eatingDaoLogger.info(
                "Added entry " + entry + ". " + person
        );
    }

    @AfterReturning("execution(* ua.mibal.eatingService.dao.EatingDao.getEatingList(..))")
    void afterReadingFromFileAdvice(JoinPoint joinPoint) {
        eatingDaoLogger.info(
                "Loaded list data from: " + joinPoint.getArgs()[0]
        );
    }
}
