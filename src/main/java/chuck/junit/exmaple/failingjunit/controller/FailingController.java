package chuck.junit.exmaple.failingjunit.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import chuck.junit.exmaple.failingjunit.service.FailingService;
import chuck.junit.exmaple.failingjunit.utils.AsyncUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FailingController {

  public static final String URL = "/failing/junit";

  private final FailingService service;
  private final AsyncUtils asyncUtils;

  @GetMapping(value = URL, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> failJunitFirst() {

    asyncUtils.fireAndForget(() -> service.fireFirstRequest());
    asyncUtils.fireAndForget(() -> service.fireSecondRequest());

    return ResponseEntity.ok("Success");
  }
  
  
}
