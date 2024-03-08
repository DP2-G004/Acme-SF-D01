
package acme.entities.audit_record;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class AuditRecord extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@ManyToOne(optional = false)
	CodeAudit					codeAudit;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "AU-\\d{4}-\\d{3}")
	String						code;

	@Past
	@NotNull
	Date						startInstant;

	@Past
	@NotNull
	Date						endInstant;

	@NotNull
	Mark						mark;

	@URL
	String						link;
}
