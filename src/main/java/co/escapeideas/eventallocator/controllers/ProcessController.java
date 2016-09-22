package co.escapeideas.eventallocator.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.escapeideas.eventallocator.EventAllocator;
import co.escapeideas.eventallocator.domain.Event;
import co.escapeideas.eventallocator.domain.Person;
import co.escapeideas.eventallocator.persistence.Store;

@Controller
@RequestMapping("/process")
public class ProcessController {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessController.class);

  @Autowired
  private Store store;

  @RequestMapping(method = RequestMethod.GET)
  public String get(Model model) {
    final EventAllocator eventAllocator = new EventAllocator(store.getEvents(), store.getPreferences());
    final Map<Event, List<Person>> allocations = eventAllocator.process();
    model.addAttribute("result", allocations.entrySet());
    return "result";
  }

}