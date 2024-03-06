
package acme.entities.banner;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	@Past
	LocalDateTime	lastInstantiationMoment;

	Duration		displayPeriod;

	String			pictureLink;

	@NotBlank
	@Length(max = 76)
	String			slogan;

	String			link;

}
