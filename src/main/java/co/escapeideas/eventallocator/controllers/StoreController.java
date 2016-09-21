package co.escapeideas.eventallocator.controllers;

import co.escapeideas.eventallocator.persistence.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/store")
public class StoreController {

  private static final Logger LOG = LoggerFactory.getLogger(StoreController.class);

  @Autowired
  private Store store;

  @RequestMapping(method = RequestMethod.GET)
  public String get(Model model) {
    store.save();
    return "redirect:/input";
  }

}