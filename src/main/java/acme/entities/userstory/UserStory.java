
package acme.entities.userstory;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {
	//Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	//Attributes
	@NotBlank
	@Length(max = 76)
	private String				title;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@Positive
	@Min(1)
	private Integer				estimatedCost;

	@NotBlank
	@Length(max = 101)
	private String				acceptanceCriteria;

	private Priority			priority;

	private String				link;
}