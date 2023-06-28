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

package ua.mibal.peopleService.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.controller.GlobalExceptionHandler;
import ua.mibal.peopleService.dao.EatingDao;
import ua.mibal.peopleService.dao.PersonDao;
import ua.mibal.peopleService.model.ApiError;
import ua.mibal.peopleService.model.Entry;
import ua.mibal.peopleService.model.Person;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
@Aspect
public class LoggingAspect {

    private final PersonDao personDao;

    public LoggingAspect(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Pointcut("execution(* ua.mibal.peopleService.controller.GlobalExceptionHandler.handleMethodArgumentNotValid(..))")
    void validationExceptionHandlingPointcut() {
    }

    @Pointcut("execution(* ua.mibal.peopleService.dao.EatingDao.save(..))")
    void addingEntryPointcut() {
    }

    @AfterReturning(
            pointcut = "validationExceptionHandlingPointcut()",
            returning = "error"
    )
    void afterValidationExceptionHandlingAdvice(ResponseEntity<Object> error) {
        LoggerFactory.getLogger(GlobalExceptionHandler.class).warn(
                "Handled errors: " + ((ApiError) error.getBody()).getMessage()
        );
    }

    @AfterReturning(
            pointcut = "addingEntryPointcut()",
            returning = "entry"
    )
    void afterAddingEntryAdvice(Entry entry) {
        Person person = personDao.getByBraceletId(entry.getBraceletId())
                .orElse(Person.emptyPerson);
        EatingDao.log.info(
                "Added entry " + entry + ". " + person
        );
    }
}
