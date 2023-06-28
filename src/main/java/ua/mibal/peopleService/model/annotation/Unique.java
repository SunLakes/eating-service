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

package ua.mibal.peopleService.model.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.mibal.peopleService.component.UniqueEntryValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Constraint(validatedBy = UniqueEntryValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    String message() default "${validatedValue} already exists. " +
                             "${personByBraceletId} already eaten.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
