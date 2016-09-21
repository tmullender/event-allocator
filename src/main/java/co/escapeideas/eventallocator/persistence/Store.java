package co.escapeideas.eventallocator.persistence;

import co.escapeideas.eventallocator.domain.Event;
import co.escapeideas.eventallocator.domain.Person;

import java.util.List;

/**
 * Created by tim on 21/09/2016.
 */
public interface Store {

    void addPeople(List<Person> people);

    void addEvents(List<Event> events);

    List<Person> getNewPeople();

    List<Event> getEvents();

    void addPreference(String personId, String[] split);

    void save();
}
