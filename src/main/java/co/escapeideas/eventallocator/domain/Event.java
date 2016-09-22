package co.escapeideas.eventallocator.domain;

import static co.escapeideas.eventallocator.persistence.KeyGenerator.generateId;

/**
 * Created by tim on 21/09/2016.
 */
public class Event {

    private final String day;
    private final String name;
    private final int capacity;
    private final String uniqueId;

    public Event(String day, String name, int capacity) {
        this.day = day;
        this.name = name;
        this.capacity = capacity;
        this.uniqueId = generateId(name, day);
    }

    public Event(String... fields) {
        this(fields[0], fields[1], Integer.valueOf(fields[2]));
    }

    @Override
    public String toString() {
        return day + '(' + name + ')';
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }
}
