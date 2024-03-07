
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

public class Risk {

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "R-\\d{3}")
	private String	code;

	@NotNull
	@Past
	private Date	identificationDate;

	@NotNull
	@Min(0)
	private Double	impact;

	@NotNull
	@Min(0)
	private Double	probability;

	@NotBlank
	@Max(100)
	private String	description;

	@URL
	private String	link;


	@Transient
	public Double getValue() {
		return this.impact * this.probability;
	}

}
