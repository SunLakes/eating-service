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

package ua.mibal.eatingService.component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import ua.mibal.eatingService.dao.EatingDao;
import ua.mibal.eatingService.dao.PersonDao;
import ua.mibal.eatingService.model.Entry;
import ua.mibal.eatingService.model.Person;
import ua.mibal.eatingService.model.annotation.Unique;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class UniqueEntryValidator implements ConstraintValidator<Unique, Entry> {

    private final EatingDao eatingDao;

    private final PersonDao personDao;

    public UniqueEntryValidator(EatingDao eatingDao, PersonDao personDao) {
        this.eatingDao = eatingDao;
        this.personDao = personDao;
    }

    @Override
    public boolean isValid(Entry entry, ConstraintValidatorContext context) {
        if (eatingDao.isExists(entry)) {
            Person person = personDao.getByBraceletId(entry.getBraceletId())
                    .orElse(Person.emptyPerson);
            HibernateConstraintValidatorContext h = context.unwrap(HibernateConstraintValidatorContext.class);
            h.addExpressionVariable("personByBraceletId", person);
            return false;
        }
        return true;
    }
}
