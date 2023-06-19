package ua.mibal.peopleService.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.model.Entry;

import javax.management.InstanceAlreadyExistsException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
public class EatingDao {

    private final int daysCount;
    private final int eatingsCount;

    private final String dataPath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<List<List<Integer>>> eatingList;

    public EatingDao(@Value("${eatingDao.dataPath}") final String dataPath,
                     @Value("${count.days}") final int daysCount,
                     @Value("${count.eatings}") final int eatingsCount) {
        this.dataPath = dataPath;
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
        // TODO verify by days that person specialize at registration form

        currentDayEatingIds.add(personId);
        updateListFile();
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
