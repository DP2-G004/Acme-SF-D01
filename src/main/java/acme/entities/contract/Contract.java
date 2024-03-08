
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
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

	@NotBlank
	@Length(max = 75)
	private String				providerName;

	@NotBlank
	@Length(max = 75)
	private String				customerName;

	@NotBlank
	@Length(max = 100)
	private String				goals;

	//que sea menor al coste del proyecto se gestionará en el servicio

	private int					budget;

}
