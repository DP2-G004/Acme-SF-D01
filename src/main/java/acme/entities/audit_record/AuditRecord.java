
package acme.entities.audit_record;

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

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.code_audit.CodeAudit;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code_audit_id", unique = false), @Index(columnList = "code", unique = false)
})
public class AuditRecord extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	CodeAudit					codeAudit;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "AU-\\d{4}-\\d{3}")
	String						code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						startInstant;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						endInstant;

	@NotNull
	Mark						mark;

	@URL
	String						link;

	private boolean				draftMode;
}
