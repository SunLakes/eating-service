package ua.mibal.peopleService.model;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class Entry {

    private int id = -1;

    private int day = -1;

    private int eating = -1;

    public Entry(int id, int day, int eating) {
        this.id = id;
        this.day = day;
        this.eating = eating;
    }

    private Entry() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return id == entry.id &&
               day == entry.day &&
               eating == entry.eating;
    }
}
