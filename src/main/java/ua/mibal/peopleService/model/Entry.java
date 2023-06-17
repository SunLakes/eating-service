package ua.mibal.peopleService.model;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class Entry {

    private int id;

    private int day;

    private int eating;

    public Entry(int id, int day, int eating) {
        this.id = id;
        this.day = day;
        this.eating = eating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getEating() {
        return eating;
    }

    public void setEating(int eating) {
        this.eating = eating;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entry{");
        sb.append("id=").append(id);
        sb.append(", day=").append(day);
        sb.append(", eating=").append(eating);
        sb.append('}');
        return sb.toString();
    }
}
