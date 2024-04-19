
package acme.entities.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{4}$", message = "Patron incorrecto, ha de comenzar por tres letras mayúsculas, seguidas de guion y cuatro digitos enteros entre cero y nueve")
	private String				code;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				summary;

	//restriccion personalizada en servicio para que no se acepten los q tengan el campo a true
	private boolean				indication;

	@Min(0)
	private Integer				cost;

	@URL
	private String				link;

	private boolean				draftMode;

	// Relationships

	@Valid
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Manager				manager;

}
