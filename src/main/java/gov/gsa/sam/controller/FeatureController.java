package gov.gsa.sam.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.gsa.sam.config.FeatureToggle;
import gov.gsa.sam.pojo.Feature;
import gov.gsa.sam.repository.ToggleRepository;

@RestController
@RequestMapping("/v1")
public class FeatureController {
	@Autowired
	ToggleRepository toggleRepo;

	private static final Logger log = LoggerFactory.getLogger(FeatureController.class);

	@FeatureToggle(value = "features.hello.world")
	@RequestMapping(value = "/helloworld", method = RequestMethod.GET, produces = "text/html")
	public ResponseEntity<String> getText() {

		log.info("request recieved to /helloworld");
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.put("Content-Type", Arrays.asList("text/html"));
		String response = "Hello world!";
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	@FeatureToggle(value = "features.hello.repeatName")
	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET, produces = "text/html")
	public ResponseEntity<String> repeatName(@PathVariable("name") String name) {

		log.info("request recieved to /hello/{name}");
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.put("Content-Type", Arrays.asList("text/html"));
		String response = "Hello, " + name;
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "text/html")
	public ResponseEntity<Feature> updateFeature(@RequestBody Feature feature) {
		log.info("request recieved to /update");
		toggleRepo.saveAndFlush(feature);
		return new ResponseEntity<Feature>(feature, HttpStatus.CREATED);
	}
}
