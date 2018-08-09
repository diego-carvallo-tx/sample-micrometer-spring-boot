package com.izeye.sample.web;

import java.util.Map;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Sample {@link RestController}.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/sample")
public class SampleController {

	private Counter myCounter = Metrics.counter("my-specific-counter");

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/call-rest-template")
	public Map<String, Object> callRestTemplate() {
		System.out.println("IncrementingMetric::myCounter++++++++++++++++++++++++++++++++++");
		myCounter.increment();
		return this.restTemplate.getForObject("https://spring.io/info", Map.class);
	}

}
