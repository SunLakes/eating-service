package ua.mibal.peopleService.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.model.Entry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
        final String data = readFile();
        return stringToList(data);
    }

    private void updateListFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File(dataPath), eatingList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFile() {
        File source = new File(dataPath);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner reader = new Scanner(source)) {
            while (reader.hasNextLine()) {
                stringBuilder.append(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    private List<List<List<Integer>>> stringToList(final String data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(data, new TypeReference<List<List<List<Integer>>>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
