
package acme.entities.progress_log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.entities.contract.Contract;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProgressLog extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Unique
	@Column(unique = true)
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}")
	private String				recordId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "contract", referencedColumnName = "contractCode")
	private Contract			contract;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				registrationMoment;

	@NotBlank
	@Min(0)
	@Max(100)
	private Integer				completeness;

	@NotBlank
	@Length(max = 100)
	private String				comment;

	@NotBlank
	@Length(max = 75)
	private String				responsiblePerson;

}
