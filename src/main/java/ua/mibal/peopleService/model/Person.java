package ua.mibal.peopleService.model;

import java.util.List;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class Person {

    // TODO

    public boolean isRegisteredForDay(int dayId) {
        // FIXME test values
        return List.of(1, 3, 5).contains(dayId);
    }
}
