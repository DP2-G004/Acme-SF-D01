
package acme.entities.notice;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends AbstractEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	Date	lastInstantiationMoment;

	@NotBlank
	@Length(max = 76)
	String	title;

	@NotBlank
	@Length(max = 76)
	String	author;

	@NotBlank
	@Length(max = 101)
	String	message;

	String	emailAdress;

	String	link;

}
