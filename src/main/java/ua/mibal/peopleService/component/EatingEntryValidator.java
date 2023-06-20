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
    }
}
