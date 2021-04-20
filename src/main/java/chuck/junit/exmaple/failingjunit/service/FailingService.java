package chuck.junit.exmaple.failingjunit.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FailingService {

  public static final String TEST_URL = "this.is.test/url/number/{number}";
  private final RestTemplate restTemplate;

  public Void fireFirstRequest() {
    final HttpMethod method = HttpMethod.GET;

    final URI uri = constructUri("1");

    final RequestEntity<Void> requestEntity = new RequestEntity<>(method, uri);

    restTemplate.exchange(requestEntity, String.class);

    return null;
  }

  public Void fireSecondRequest() {
    final HttpMethod method = HttpMethod.POST;
    final URI uri = constructUri("2");

    final RequestEntity<Void> requestEntity = new RequestEntity<>(method, uri);

    try {
      Thread.sleep(1000l);
    } catch (final InterruptedException e) {
    }

    restTemplate.exchange(requestEntity, String.class);

    return null;
  }

  public URI constructUri(final String number) {
    final Map<String, String> params = new HashMap<>(1);
    params.put("number", number);
    return UriComponentsBuilder.fromUriString(TEST_URL).buildAndExpand(params).toUri();
  }

}
