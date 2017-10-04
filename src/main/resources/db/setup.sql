CREATE TABLE public.feature
(
    feature_key character varying(100),
    feature_value boolean,
    CONSTRAINT feature_PK PRIMARY KEY (feature_key)
);
    
INSERT INTO public.feature(
	feature_key, feature_value)
	VALUES ('features.hello.world.enabled', true);
    
INSERT INTO public.feature(
	feature_key, feature_value)
	VALUES ('features.hello.repeatName.enabled', false);