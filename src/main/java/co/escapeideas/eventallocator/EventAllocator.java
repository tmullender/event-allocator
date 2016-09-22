package co.escapeideas.eventallocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.escapeideas.eventallocator.comparators.GroupComparator;
import co.escapeideas.eventallocator.domain.Event;
import co.escapeideas.eventallocator.domain.Person;

/**
 * Notice: This software is proprietary to CME, its affiliates, partners and/or licensors.  Unauthorized copying, distribution or use is strictly prohibited.  All rights reserved.
 * Created with IntelliJ IDEA.
 * User: e20856
 * Date: 22/09/2016
 * Time: 13:33
 */
public class EventAllocator {

  private static final Logger LOG = LoggerFactory.getLogger(EventAllocator.class);

  private final Map<String, Event> events;
  private final Map<Person, String[]> preferences;
  private final Map<Event, List<Person>> allocations = new HashMap<>();
  private final GroupComparator sortOrder;

  public EventAllocator(final Map<String, Event> events, final Map<Person, String[]> preferences) {
    LOG.debug("Doing event allocation for {} and {}", events, preferences);
    this.events = events;
    this.preferences = preferences;
    sortOrder = new GroupComparator();
  }


  public Map<Event, List<Person>> process() {
    boolean updated = true;
    while (updated) {
      updated = false;
      final List<Person> people = new ArrayList<>(preferences.keySet());
      Collections.sort(people, sortOrder);
      for(Person person : people) {
        final String[] preference = preferences.get(person);
        for(String eventId : preference) {
          if(allocatePersonToEvent(person, eventId, preference)) {
            updated = true;
            break;
          }
        }
      }
    }
    return allocations;
  }

  private boolean allocatePersonToEvent(final Person person, final String eventId, final String[] preference) {
    LOG.debug("allocating {} to {} from {}", person, eventId, Arrays.toString(preference));
    final Event event = events.get(eventId);
    List<Person> allocated = getAllocatedPeople(event);
    if(allocated.size() < event.getCapacity() && meetsConstraints(person, event)) {
      allocated.add(person);
      if(preference.length <= 1) {
        preferences.remove(person);
      } else {
        final String[] updated = removeAllocated(eventId, preference);
        preferences.put(person, updated);
      }
      return true;
    }
    return false;
  }

  private boolean meetsConstraints(Person person, Event event) {
    for (Event existing : allocations.keySet()){
      if (event.getDay().equals(existing.getDay()) && allocations.get(existing).contains(person)){
        return false;
      }
    }
    return true;
  }

  private String[] removeAllocated(final String eventId, final String[] preference) {
    return Arrays.stream(preference).filter(x -> !x.equals(eventId)).toArray(String[]::new);
  }

  private List<Person> getAllocatedPeople(final Event event) {
    List<Person> allocated = allocations.get(event);
    if(allocated == null) {
      allocated = new ArrayList<>();
      allocations.put(event, allocated);
    }
    return allocated;
  }
}
