package chuck.junit.exmaple.failingjunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import chuck.junit.exmaple.failingjunit.controller.FailingController;
import chuck.junit.exmaple.failingjunit.service.FailingService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FailingJunitApplicationTests {

  private MockRestServiceServer mockServer;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private FailingService service;

  @BeforeEach
  public void setUp() {
    mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
  }

  @AfterEach
  public void tearDown() {
    mockServer.verify();
    mockServer.reset();
  }

  @Test
  void testFailsFirst() throws Exception {
    // GIVEN

    // first api
    URI firstUri = service.constructUri("1");
    mockServer.expect(ExpectedCount.once(), requestTo(firstUri)).andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    // second api
    URI secondUri = service.constructUri("2");
    mockServer.expect(ExpectedCount.once(), requestTo(secondUri)).andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess());

    // test uri
    URI testUri = URI.create(FailingController.URL);

    // WHEN
    String responseString = mockMvc.perform(get(testUri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

    // THEN
    assertEquals("Success", responseString);
  }

  @Test
  void testFailsSecond() throws Exception {
    // GIVEN

    // first api
    URI firstUri = service.constructUri("1");
    mockServer.expect(ExpectedCount.between(0, 1), requestTo(firstUri)).andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    // second api
    URI secondUri = service.constructUri("2");
    mockServer.expect(ExpectedCount.between(0, 1), requestTo(secondUri)).andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess());

    // test uri
    URI testUri = URI.create(FailingController.URL);

    // WHEN
    String responseString = mockMvc.perform(get(testUri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

    // THEN
    assertEquals("Success", responseString);
  }

}
