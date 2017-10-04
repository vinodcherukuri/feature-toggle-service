package gov.gsa.sam.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "feature")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feature {

	@Id
	@Column(name = "feature_key")
	private String featurekey;

	@Column(name = "feature_value")
	private Boolean featureValue;

	public String getFeatureKey() {
		return featurekey;
	}

	public void setFeatureKey(String featurekey) {
		this.featurekey = featurekey;
	}

	public Boolean getFeatureValue() {
		return featureValue;
	}

	public void setFeatureValue(Boolean featureValue) {
		this.featureValue = featureValue;
	}

}
