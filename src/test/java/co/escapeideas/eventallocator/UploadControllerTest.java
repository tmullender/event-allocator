package co.escapeideas.eventallocator;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class UploadControllerTest {

  @Test
  public void testGet() throws Exception {
    final HtmlPage page = new WebClient().getPage("http://localhost:8080");
    final String names = page.getElementById("upload-names").getAttribute("type");
    final String events = page.getElementById("upload-events").getAttribute("type");
    assertEquals("file", names);
    assertEquals("file", events);
  }

  @Test
  public void testPost() throws Exception {
    final HtmlPage page = new WebClient().getPage("http://localhost:8080");
    final HtmlForm form = page.getFormByName("upload");
    final HtmlInput names = form.getInputByName("names");
    final File nameUpload = new File("src/test/resources/names.txt");
    names.setValueAttribute(nameUpload.getAbsolutePath());
    final HtmlInput events = form.getInputByName("events");
    final File eventUpload = new File("src/test/resources/events.txt");
    events.setValueAttribute(eventUpload.getAbsolutePath());
    final HtmlPage result = form.getInputByName("submit").click();
    assertNotNull(result.getFormByName("upload"));
  }
}