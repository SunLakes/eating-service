package ua.mibal.peopleService.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.model.Person;

import java.util.Optional;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
public class PersonDao {

    private final String dbUrl;

    public PersonDao(@Value("${personDao.dbUrl}") final String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public synchronized Optional<Person> getById(final int id) {
        // TODO
        return Optional.empty();
    }
}
