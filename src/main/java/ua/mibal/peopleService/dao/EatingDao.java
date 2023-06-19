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
import ua.mibal.peopleService.model.Person;

import javax.management.InstanceAlreadyExistsException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    private final PersonDao personDao;
    private final List<List<List<Integer>>> eatingList;

    private final int daysCount;
    private final int eatingsCount;

    public EatingDao(@Value("${eatingDao.dataPath}") final String dataPath,
                     final PersonDao personDao,
                     @Value("${count.days}") final int daysCount,
                     @Value("${count.eatings}") final int eatingsCount) {
        this.dataPath = dataPath;
        this.personDao = personDao;
        this.eatingList = getEatingList();
        this.daysCount = daysCount;
        this.eatingsCount = eatingsCount;
    }

    public synchronized void save(final Entry entry) throws IllegalArgumentException, InstanceAlreadyExistsException {
        final int dayId = entry.getDay();
        final int eatingId = entry.getEating();
        final int personId = entry.getId();

        if (personId == -1) {
            throw new IllegalArgumentException("Entry person id must be initialized");
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

        List<Integer> currentDayEatingIds = eatingList.get(dayId - 1).get(eatingId - 1);
        if (currentDayEatingIds.contains(personId)) {
            throw new InstanceAlreadyExistsException(format(
                    "Entry already exists: person with id '%d' has already eaten. %s", personId, entry
            ));
        }

        final Optional<Person> optionalPerson = personDao.getById(personId);
        if (optionalPerson.isEmpty()) {
            throw new IllegalArgumentException(format(
                    "Person with id='%d' doesnt exists", personId
            ));
        }
        if (!optionalPerson.get().isRegisteredForDay(dayId)) {
            throw new IllegalArgumentException(format(
                    "Person with id='%d' did not register for a day id='%d'", personId, dayId
            ));
        }

        currentDayEatingIds.add(personId);
        updateListFile();
        log.info("Added entry " + entry);
    }

    private List<List<List<Integer>>> getEatingList() {
        try {
            return objectMapper.readValue(new File(dataPath),
                    new TypeReference<List<List<List<Integer>>>>() {
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
