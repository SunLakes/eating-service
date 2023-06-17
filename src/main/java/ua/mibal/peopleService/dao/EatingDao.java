package ua.mibal.peopleService.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.model.Entry;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
public class EatingDao {

    private final static int DAYS_COUNT = 7;

    private final static int EATING_COUNT = 3;

    private final String dataPath;

    private final List<List<List<Integer>>> eatingList;

    public EatingDao(@Value("${eatingDao.dataPath}") final String dataPath) {
        this.dataPath = dataPath;
        this.eatingList = this.getEatingList();
    }

    public void save(final Entry entry) {
        final int dayId = entry.getDay();
        final int eatingId = entry.getEating();
        final int personId = entry.getId();

        if (personId == -1) {
            throw new IllegalArgumentException("Entry person id must be initialized");
        }
        if (dayId == -1) {
            throw new IllegalArgumentException("Entry day id must be initialized");
        }
        if (eatingId == -1) {
            throw new IllegalArgumentException("Entry eating id must be initialized");
        }

        List<Integer> currentDayEatingIds = eatingList.get(dayId - 1).get(eatingId - 1);
        if (currentDayEatingIds.contains(personId)) {
            throw new IllegalArgumentException(format(
                    "Entry already exists - person with id '%d' has already eaten", personId
            ));
        }
        // TODO verify by days that person specialize at registration form

        currentDayEatingIds.add(personId);
        updateListFile();
    }

    private List<List<List<Integer>>> getEatingList() {
        // TODO read from file / new list
        final List<List<List<Integer>>> result = new ArrayList<>();
        for (int i = 0; i < DAYS_COUNT; i++) {
            final List<List<Integer>> list = new ArrayList<>();
            for (int j = 0; j < EATING_COUNT; j++) {
                list.add(new ArrayList<>());
            }
            result.add(list);
        }
        return result;
    }

    private void updateListFile() {
        // TODO update file to keep data saved
    }
}
