package co.escapeideas.eventallocator.persistence;

import java.util.List;
import java.util.Map;

import co.escapeideas.eventallocator.domain.Event;
import co.escapeideas.eventallocator.domain.Person;

/**
 * Created by tim on 21/09/2016.
 */
public interface Store {

    void addPeople(List<Person> people);

    void addEvents(List<Event> events);

    List<Person> getNewPeople();

    Map<String, Event> getEvents();

    Map<Person,String[]> getPreferences();

    void addPreference(String personId, String[] split);

    void save();

}
