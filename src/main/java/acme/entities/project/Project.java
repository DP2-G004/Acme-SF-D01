
package acme.entities.project;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {
	//Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	//Attributes
	@Pattern(regexp = "[A-Z]{3}-\\d{4}")
	private String				code;

	@NotBlank
	@Length(max = 76)
	private String				title;

	@NotBlank
	@Length(max = 101)
	private String				abstracto;

	//restriccion personalizada en servicio para que no se acepten los q tengan el campo a true
	private Boolean				indication;

	@Positive
	private Integer				cost;

	private String				link;

	//relacion con user stories, el manager puede elicitar muchas de un proyecto
}
