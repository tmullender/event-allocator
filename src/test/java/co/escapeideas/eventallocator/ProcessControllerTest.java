package co.escapeideas.eventallocator;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;

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
public class ProcessControllerTest {

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
    for (int i=0; i<25; i++){
      submitInput();
    }
  }

  private void submitInput() throws IOException {
    final HtmlPage inputPage = new WebClient().getPage("http://localhost:8080/input");
    final Iterator<DomElement> events = inputPage.getElementById("events").getChildElements().iterator();
    events.next().click();
    events.next().click();
    events.next().click();
    final HtmlButtonInput submit = (HtmlButtonInput) inputPage.getElementById("button");
    submit.click();
  }

  @Test
  public void testGet() throws Exception {
    final HtmlPage processPage = new WebClient().getPage("http://localhost:8080/process");
    final DomNodeList<DomElement> events = processPage.getElementsByTagName("h5");
    assertEquals(3, events.size());
    HtmlUnorderedList list = (HtmlUnorderedList) processPage.getElementById("ID-635226");
    assertEquals(8, list.getChildElementCount());
    list = (HtmlUnorderedList) processPage.getElementById("ID-243103917");
    assertEquals(6, list.getChildElementCount());
    list = (HtmlUnorderedList) processPage.getElementById("ID-2118511976");
    assertEquals(7, list.getChildElementCount());
  }

}