package gov.gsa.sam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.gsa.sam.config.FeatureToggle;
import gov.gsa.sam.pojo.Feature;
import gov.gsa.sam.repository.ToggleRepository;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/feature/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureController {
	@Autowired
	ToggleRepository toggleRepo;

	private static final Logger log = LoggerFactory.getLogger(FeatureController.class);

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ResponseEntity<String> getText() {
		log.info("request received to /helloworld");
		String response = "Hello world!";
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@FeatureToggle(value = "comp.rms.feature.print")
	@RequestMapping(value = "/print/{server}/{app}", method = RequestMethod.GET)
	public ResponseEntity<List<Feature>> getPrintAll(
			@ApiParam(value = "server name", required = true) @PathVariable String server,
			@ApiParam(value = "application name", required = true) @PathVariable String app) {

		log.info("request received to /print");
		String key = server + "." + app + "%";
		return new ResponseEntity<>(toggleRepo.getFeatures(key), HttpStatus.OK);
	}

	/**
	 * API endpoint to insert/update featureKey values to true/false.
	 * 
	 * @param feature
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Feature> updateFeature(@RequestBody Feature feature) {
		log.info("request received to /update");
		toggleRepo.saveAndFlush(feature);
		return new ResponseEntity<Feature>(feature, HttpStatus.CREATED);
	}
}
