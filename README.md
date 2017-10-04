# feature-toggle-demo
This is a demo implementation of feature toggles using concepts of Toggle Points, Toggle Router, and Toggle Configuration [3].

## Toggle Points
Toggle points are "forks in the road" that determine which code path will execute based on a feature toggle. Toggle points should be placed as close the entry point of execution as possible. Request mappings are excelent places for toggle points.
### Annotation (Declarative) approach for request mappings
Toggle points can be placed on request methods of RestControllers using the ```@FeatureToggle(value = "features.name.here")``` annotation. The ```value``` parameter takes the name of the feature the toggle point should toggle around.   Methods with this annotation are advised by ```@Around``` advice from ```FeatureToggleAspect```. This aspect uses the ```ToggleRouter``` to check feature state and either proceeds with the request method call or short circuits it and provides a HTTP 404 response. Advice and aspects are concepts from Aspect Oriented Programming[1]. 

Example:

```
@FeatureToggle(value = "features.hello.world")
@RequestMapping(value = "/helloworld", method = RequestMethod.GET, produces = "text/html")
public ResponseEntity<String> getText() {
  log.info("request recieved to /helloworld");
  MultiValueMap<String, String> headers = new HttpHeaders();
  headers.put("Content-Type", Arrays.asList("text/html"));
  String response = "Hello world!";
  return new ResponseEntity<>(response, headers, HttpStatus.OK);
}
```
### Basic approach
The ```ToggleRouter``` can be used in code if needed or as part of a guard clause. 

Example:
```
if (toggleRouter.isFeatureEnabled(featureName) {
  newImplementation();
} else {
  oldImplementation();
}
```

Guard clause example:
```
@RequestMapping(value = "/foo/{type}", method = RequestMethod.GET, produces = "text/html")
public ResponseEntity<String> repeatName(@PathVariable("type") String name) {
  // Guard clause against unsupported/disabled types
  if (!toggleRouter.isFeatureEnabled(name)) {
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  } 
  // pass through to implementation...
}
```

## Toggle Router
The ```ToggleRouter``` component makes the decision if the feature is enabaled and provides that decision as a true/false to the Toggle Points. The Toggle Router uses Toggle Configuration and Toggle Context to make this decision. In this demo the Toggle Router uses a simple implementation based soley on the Toggle Configuration state.

## Toggle Configuration
The ```ToggleConfiguration``` checks for a property in the environment provided by the Toggle Router and returns a true/false value if the property is enabled. 

## Notes
The DemoController has two endpoints ```/helloworld``` and ```/hello/{name}``` controlled by features.hello.world (default enabled) and features.hello.repeatName (default disabled). Any way the .enabled suffix can be changed for these features will be picked up by the ```ToggleConfiguration```. This could be environment variables, specific profile properties files, cloud config server, or many other options [2].

## References
1. https://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html
2. https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
3. https://martinfowler.com/articles/feature-toggles.html
