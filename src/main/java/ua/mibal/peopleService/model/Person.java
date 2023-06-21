package ua.mibal.peopleService.model;

import java.util.Set;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class Person {

    public static final Person emptyPerson = new Person(-1, -1, "null", Set.of());

    private int id;

    private int braceletId;

    private String name;

    private Set<Integer> days;

    private Person() {
    }

    public Person(int id, int braceletId, String name, Set<Integer> days) {
        this.id = id;
        this.braceletId = braceletId;
        this.name = name;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public int getBraceletId() {
        return braceletId;
    }

    public String getName() {
        return name;
    }

    public boolean isRegisteredForDay(int dayId) {
        return days.contains(dayId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", braceletId=").append(braceletId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", days=").append(days);
        sb.append('}');
        return sb.toString();
    }
}
