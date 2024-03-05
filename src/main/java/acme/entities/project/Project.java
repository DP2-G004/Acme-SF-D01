
package acme.entities.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {
	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{3}-\\d{4}")
	private String				code;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				summary;

	//restriccion personalizada en servicio para que no se acepten los q tengan el campo a true
	private Boolean				indication;

	@Positive
	private Integer				cost;

	@URL
	private String				link;

	// Relationships

	@Valid
	@ManyToOne(optional = false)
	private Manager				manager;

}
