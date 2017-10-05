package gov.gsa.sam.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.gsa.sam.pojo.Feature;
import gov.gsa.sam.repository.ToggleRepository;

@Component("toggleConfiguration")
public class ToggleConfiguration {

	private static final Logger log = LoggerFactory.getLogger(ToggleConfiguration.class);

	@Autowired
	ToggleRepository toggleRepo;

	public boolean getFeatureToggleState(String featureKey) {
		Feature feature = toggleRepo.getFeatureByKey(featureKey);
		Boolean featureValue = false;
		if (feature != null) {
			featureValue = feature.getFeatureValue();
		}
		log.info("Feature : " + featureKey + " : " + featureValue);
		return featureValue;
	}
}
