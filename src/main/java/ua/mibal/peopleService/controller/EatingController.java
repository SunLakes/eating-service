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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mibal.peopleService.component.EatingEntryValidator;
import ua.mibal.peopleService.dao.EatingDao;
import ua.mibal.peopleService.model.Entry;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@RestController
@RequestMapping("/eating")
public class EatingController {

    private final EatingDao eatingDao;

    private final EatingEntryValidator eatingEntryValidator;

    public EatingController(EatingDao eatingDao, EatingEntryValidator eatingEntryValidator) {
        this.eatingDao = eatingDao;
        this.eatingEntryValidator = eatingEntryValidator;
    }

    @PostMapping
    Entry addEntry(@RequestBody Entry entry) {
        eatingEntryValidator.validate(entry);
        eatingDao.save(entry);
        return entry;
    }
}
