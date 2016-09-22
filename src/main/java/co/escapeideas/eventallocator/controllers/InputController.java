package co.escapeideas.eventallocator.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.escapeideas.eventallocator.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import co.escapeideas.eventallocator.persistence.Store;

@Controller
@RequestMapping("/input")
public class InputController {

  private static final Logger LOG = LoggerFactory.getLogger(InputController.class);

  @Autowired
  private Store store;

  @RequestMapping(method = RequestMethod.GET)
  public String get(Model model) {
    final List<Person> newPeople = store.getNewPeople();
    Collections.sort(newPeople, new PersonComparator());
    model.addAttribute("people", newPeople);
    model.addAttribute("events", store.getEvents().values());
    return "input";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String post(@RequestParam("name") String name,
                     @RequestParam("order") String order, Model model) throws IOException {
    LOG.debug("post {} {}", name, order);
    store.addPreference(name, order.split(","));
    return get(model);
  }

  private class PersonComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
      final int group = p1.getGroup().compareTo(p2.getGroup());
      return group == 0 ? p1.getName().compareTo(p2.getName()) : group;
    }
  }
}