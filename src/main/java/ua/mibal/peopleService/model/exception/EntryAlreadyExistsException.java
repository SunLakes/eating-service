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

package ua.mibal.peopleService.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.mibal.peopleService.model.Entry;
import ua.mibal.peopleService.model.Person;

import static java.lang.String.format;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class EntryAlreadyExistsException extends RuntimeException {

    public EntryAlreadyExistsException(Entry entry, Person person) {
        super(format(
                "Entry already exists: person with bracelet id '%d' and name '%s' has already eaten. %s %s",
                entry.getBraceletId(), person.getName(), entry, person
        ));
    }
}
