package gov.gsa.sam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.gsa.sam.pojo.Feature;

public interface ToggleRepository extends JpaRepository<Feature, Integer> {

	@Query(value = "select f.* from public.feature f where f.feature_key = :key", nativeQuery = true)
	public Feature getFeatureByKey(@Param("key") String key);
}
