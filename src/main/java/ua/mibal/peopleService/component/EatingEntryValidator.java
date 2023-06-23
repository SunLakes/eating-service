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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.dao.PersonDao;
import ua.mibal.peopleService.model.Entry;
import ua.mibal.peopleService.model.Person;

import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
public class EatingEntryValidator {

    private final PersonDao personDao;

    private final int daysCount;

    private final int eatingsCount;

    public EatingEntryValidator(final PersonDao personDao,
                                @Value("${count.days}") final int daysCount,
                                @Value("${count.eatings}") final int eatingsCount) {
        this.personDao = personDao;
        this.daysCount = daysCount;
        this.eatingsCount = eatingsCount;
    }

    public void validate(final Entry entry) {
        final int dayId = entry.getDay();
        final int eatingId = entry.getEating();
        final int braceletId = entry.getBraceletId();

        if (braceletId == -1) {
            throw new IllegalArgumentException(
                    "Person bracelet id must be initialized");
        }
        if (!(1 <= dayId && dayId <= daysCount)) {
            throw new IllegalArgumentException(format(
                    "Day id='%d' must be in range [%d, %d]", dayId, 1, daysCount
            ));
        }
        if (!(1 <= eatingId && eatingId <= eatingsCount)) {
            throw new IllegalArgumentException(format(
                    "Eating id='%d' must be in range [%d, %d]", eatingId, 1, eatingsCount
            ));
        }

        final Optional<Person> optionalPerson = personDao.getByBraceletId(braceletId);
        if (optionalPerson.isEmpty()) {
            throw new IllegalArgumentException(format(
                    "Person with bracelet id='%d' doesnt exists", braceletId
            ));
        }
        final Person person = optionalPerson.get();
        if (!person.isRegisteredForDay(dayId)) {
            throw new IllegalArgumentException(format(
                    "Person with bracelet id='%d' and name='%s' did not register for a day id='%d' %s",
                    braceletId, person.getName(), dayId, person
            ));
        }
    }
}
