package co.escapeideas.eventallocator.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.escapeideas.eventallocator.domain.Event;
import co.escapeideas.eventallocator.domain.Person;
import co.escapeideas.eventallocator.persistence.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class UploadController {

  private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

  @Autowired
  private Store store;

  @RequestMapping(method = RequestMethod.GET)
  public String get() {
    return "landing";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String post(@RequestParam("names") final MultipartFile names,
                     @RequestParam("events") final MultipartFile events,
                     Model model) throws IOException {
    final List<Person> people = readListOfPeople(names);
    final List<Event> eventList = readListOfEvents(events);
    LOG.debug("people={} events={}", people, eventList);
    store.addPeople(people);
    store.addEvents(eventList);
    return "landing";
  }

  private List<Person> readListOfPeople(MultipartFile names) throws IOException {
    return (List<Person>) readList(names, Person.class);
  }

  private List<Event> readListOfEvents(MultipartFile events) throws IOException {
    return (List<Event>) readList(events, Event.class);
  }


  private List readList(@RequestParam("names") MultipartFile names, Class type) throws IOException {
    final List<Object> people = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(names.getInputStream()))) {
      final Constructor constructor = type.getConstructor(String[].class);
      String line;
      while ((line = reader.readLine()) != null){
        String[] data = line.split(",");
        LOG.debug("adding line {}", Arrays.toString(data));
        people.add(constructor.newInstance(new Object[]{data}));
      }
    } catch (ReflectiveOperationException e) {
      e.printStackTrace();
    }
    return people;
  }


}