
package acme.entities.objective;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Objective extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.DATE)
	@Past
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 76)
	private String				title;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@NotNull
	private ObjectivePriority	priority;

	@NotNull
	private Boolean				status;

	//Need to be checked in service
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Duration			duration;

	@URL
	private String				link;

}
