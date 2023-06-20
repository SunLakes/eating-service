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

import javax.management.InstanceAlreadyExistsException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
public class EatingDao {

    private final static Logger log = LoggerFactory.getLogger(EatingDao.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final String dataPath;
    private final List<List<Set<Integer>>> eatingList;

    public EatingDao(@Value("${eatingDao.dataPath}") final String dataPath) {
        this.dataPath = dataPath;
        this.eatingList = getEatingList();
    }

    public synchronized void save(final Entry entry) throws IllegalArgumentException, InstanceAlreadyExistsException {
        final int dayId = entry.getDay();
        final int eatingId = entry.getEating();
        final int personBraceletId = entry.getBraceletId();

        Set<Integer> currentDayEatingIds = eatingList.get(dayId - 1).get(eatingId - 1);
        if (!currentDayEatingIds.add(personBraceletId)) {
            throw new InstanceAlreadyExistsException(format(
                    "Entry already exists: person with bracelet id '%d' has already eaten. %s", personBraceletId, entry
            ));
        }
        updateListFile();
        log.info("Added entry " + entry);
    }

    private List<List<Set<Integer>>> getEatingList() {
        try {
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
}
