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

package ua.mibal.peopleService.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.model.Entry;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
public class EatingDao {

    public final static Logger log = LoggerFactory.getLogger(EatingDao.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final String dataPath;
    private final List<List<Set<Integer>>> eatingList;

    public EatingDao(@Value("${eatingDao.dataPath}") final String dataPath) {
        this.dataPath = dataPath;
        this.eatingList = getEatingList();
    }

    public synchronized Entry save(final Entry entry) {
        final int dayId = entry.getDayId();
        final int eatingId = entry.getEatingId();
        final int personBraceletId = entry.getBraceletId();

        Set<Integer> currentDayEatingIds = eatingList.get(dayId - 1).get(eatingId - 1);
        currentDayEatingIds.add(personBraceletId);
        updateListFile();

        return entry;
    }

    private List<List<Set<Integer>>> getEatingList() {
        try {
            log.info("Loaded list data from: " + dataPath);
            return objectMapper.readValue(new File(dataPath),
                    new TypeReference<List<List<Set<Integer>>>>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateListFile() {
        try {
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File(dataPath), eatingList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExists(Entry entry) {
        final int dayId = entry.getDayId();
        final int eatingId = entry.getEatingId();
        final int braceletId = entry.getBraceletId();

        try {
            Set<Integer> currentDayEatingIds = eatingList.get(dayId - 1).get(eatingId - 1);
            return currentDayEatingIds.contains(braceletId);
        } catch (Exception e) {
            return false;
        }
    }
}
