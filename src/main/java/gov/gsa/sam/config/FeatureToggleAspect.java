package gov.gsa.sam.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {

	@Autowired
	private ToggleRouter toggleRouter;
	private static final Logger log = LoggerFactory.getLogger(FeatureToggleAspect.class);

	@Around(value = "@annotation(FeatureToggle) && @annotation(featureToggle)", argNames = "featureToggle")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
		String featureName = featureToggle.value();
		String methodSignature = joinPoint.getSignature().toString();
		boolean isFeatureEnabled = toggleRouter.isFeatureEnabled(featureName);
		log.info("%s %s: %s", methodSignature, featureName, isFeatureEnabled);
		if (isFeatureEnabled) {
			return joinPoint.proceed();
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
