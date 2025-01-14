
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
	@Index(columnList = "completeness"), @Index(columnList = "record"), @Index(columnList = "contract_id")
})
public class Progress extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^PG-[A-Z]{1,2}-[0-9]{4}$")
	protected String			record;

	@Range(min = 0, max = 100)
	private double				completeness;

	@Length(max = 100)
	@NotBlank
	protected String			comment;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				registration;

	@Length(max = 75)
	@NotBlank
	protected String			responsable;

	private boolean				draftMode;

	@Valid
	@ManyToOne
	@NotNull
	private Contract			contract;

}
