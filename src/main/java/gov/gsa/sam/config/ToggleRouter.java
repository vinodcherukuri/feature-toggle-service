package gov.gsa.sam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("toggleRouter")
public class ToggleRouter {

	@Autowired
	private ToggleConfiguration toggleConfiguration;

	public boolean isFeatureEnabled(String feature) {
		return toggleConfiguration.getFeatureToggleState(feature);
	}
}
