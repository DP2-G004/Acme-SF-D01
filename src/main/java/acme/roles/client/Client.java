
package acme.roles.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends AbstractRole {

	//Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "CLI-[0-9]{4}")
	private String				identification;

	@NotBlank
	@Length(max = 75)
	private String				companyName;
	@NotNull
	private ClientType			type;

	@NotBlank
	@Email
	private String				email;

	@URL
	private String				link;
}
