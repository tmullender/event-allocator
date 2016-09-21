package co.escapeideas.eventallocator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Notice: This software is proprietary to CME, its affiliates, partners and/or licensors.  Unauthorized copying, distribution or use is strictly prohibited.  All rights reserved.
 * Created with IntelliJ IDEA.
 * User: e20856
 * Date: 25/04/2016
 * Time: 10:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class RESTfulControllerTest {

  private RestTemplate template = new TestRestTemplate();

  @Test
  public void testGet() throws Exception {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

    final ResponseEntity<Map> result = template.exchange("http://localhost:8080", HttpMethod.GET, entity, Map.class);
    final Map map = result.getBody();
    assertTrue(map.containsKey("PATH"));
    assertEquals(map.get("HOME"), System.getProperty("user.home"));
  }

  @Test
  public void testPost() throws Exception {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    final MultiValueMap params = new LinkedMultiValueMap();
    params.put("name", Arrays.asList("Tim"));
    params.put("age", Arrays.asList("21"));
    HttpEntity<Map> entity = new HttpEntity<Map>(params, headers);

    final ResponseEntity<Map> result = template.exchange("http://localhost:8080", HttpMethod.POST, entity, Map.class);
    final Map map = new HashMap<>();
    result.getBody().forEach((key,value) -> {map.put(key, Arrays.asList(value));});
    assertEquals(params, map);
  }
}