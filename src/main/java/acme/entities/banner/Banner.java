
package acme.entities.banner;

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
public class Banner extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	Date						instantiationMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	Date						displayMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	Date						endOfDisplay;

	@NotBlank
	@URL
	String						pictureLink;

	@NotBlank
	@Length(max = 75)
	String						slogan;

	@NotBlank
	@URL
	String						link;

}
