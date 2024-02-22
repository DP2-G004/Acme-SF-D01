
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Claim extends AbstractEntity {
	//Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	//Attributes
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "C-\\d{4}")
	private String				code;

	@Past
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 76)
	private String				heading;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@NotBlank
	@Length(max = 101)
	private String				department;

	private String				email;

	private String				link;
}
