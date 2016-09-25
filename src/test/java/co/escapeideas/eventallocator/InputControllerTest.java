package co.escapeideas.eventallocator;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Notice: This software is proprietary to CME, its affiliates, partners and/or licensors.  Unauthorized copying, distribution or use is strictly prohibited.  All rights reserved.
 * Created with IntelliJ IDEA.
 * User: e20856
 * Date: 25/04/2016
 * Time: 10:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class InputControllerTest {

  @Before
  public void setup() throws IOException {
    final HtmlPage page = new WebClient().getPage("http://localhost:8080");
    final HtmlForm form = page.getFormByName("upload");
    final HtmlInput names = form.getInputByName("names");
    final File nameUpload = new File("src/test/resources/names.txt");
    names.setValueAttribute(nameUpload.getAbsolutePath());
    final HtmlInput events = form.getInputByName("events");
    final File eventUpload = new File("src/test/resources/events.txt");
    events.setValueAttribute(eventUpload.getAbsolutePath());
    form.getInputByName("submit").click();
  }

  @Test
  public void testGet() throws Exception {
    final HtmlPage page = new WebClient().getPage("http://localhost:8080/input");
    final HtmlSelect select = (HtmlSelect) page.getElementById("selection-name");
    assertEquals(30, select.getOptionSize());
    final HtmlElement events = (HtmlElement) page.getElementById("selection-events");
    assertEquals(5, events.getChildElementCount());
  }

  @Test
  public void testPost() throws Exception {
    final HtmlPage page = new WebClient().getPage("http://localhost:8080/input");
    final Iterator<DomElement> events = page.getElementById("selection-events").getChildElements().iterator();
    events.next().click();
    events.next().click();
    events.next().click();
    final HtmlButtonInput submit = (HtmlButtonInput) page.getElementById("button");
    final HtmlPage result = submit.click();
    final HtmlSelect select = (HtmlSelect) result.getElementById("selection-name");
    assertEquals(29, select.getOptionSize());
  }
}