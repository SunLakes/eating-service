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

package ua.mibal.peopleService.component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.mibal.peopleService.dao.EatingDao;
import ua.mibal.peopleService.model.Entry;
import ua.mibal.peopleService.model.annotation.Unique;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class UniqueEntryValidator implements ConstraintValidator<Unique, Entry> {

    private final EatingDao eatingDao;

    public UniqueEntryValidator(EatingDao eatingDao) {
        this.eatingDao = eatingDao;
    }

    @Override
    public boolean isValid(Entry entry, ConstraintValidatorContext context) {
        return !eatingDao.isExists(entry);
    }
}