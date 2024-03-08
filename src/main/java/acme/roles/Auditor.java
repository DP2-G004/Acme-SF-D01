
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	//Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	String						firm;

	@NotBlank
	@Length(max = 25)
	@Column(unique = true)
	String						professionalID;

	@NotBlank
	@Length(max = 100)
	String						certifications;

	@URL
	String						link;
}
