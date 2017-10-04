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

	public boolean getFeatureToggleState(String featureName) {
		String featureEnabledPath = featureName + ".enabled";

		Feature feature = toggleRepo.getFeatureByKey(featureEnabledPath);
		log.info(feature.getFeatureKey() + " : " + feature.getFeatureValue());
		log.info("Feature : " + featureEnabledPath + feature.getFeatureValue());
		return feature.getFeatureValue();
	}
}
