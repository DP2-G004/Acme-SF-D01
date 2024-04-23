
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import acme.roles.client.Client;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contract extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "project_code", referencedColumnName = "code")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Project				project;

	@NotBlank
	@Unique
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				contractCode;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				instantiationMoment;

	@Valid
	@ManyToOne
	@JoinColumn(name = "provider_name_id", nullable = false)
	private Project				providerName;

	@Valid
	@ManyToOne
	@JoinColumn(name = "customer_name_id", nullable = false)
	private Client				customerName;

	@NotBlank
	@Length(max = 100)
	private String				goals;

	public Money				budget;

	private boolean				draftMode;

}
