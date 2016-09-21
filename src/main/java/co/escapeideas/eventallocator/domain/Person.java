package co.escapeideas.eventallocator.domain;

import static co.escapeideas.eventallocator.persistence.KeyGenerator.generateId;

/**
 * Created by tim on 21/09/2016.
 */
public class Person {

    private final String name;
    private final String group;
    private final String uniqueId;

    public Person(String name, String group) {
        this.name = name;
        this.group = group;
        this.uniqueId = generateId(name, group);
    }

    public Person(String... fields) {
        this(fields[0], fields[1]);
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return name + '(' + group + ')' ;
    }
}
