
package acme.entities.code_audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.audit_record.Mark;
import acme.entities.project.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudit extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	@Valid
	@ManyToOne(optional = false)
	Project						project;

	@Valid
	@ManyToOne(optional = false)
	Auditor						auditor;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-\\d{3}")
	String						code;

	@NotNull
	CodeAuditType				type;

	Mark						mark;

	@URL
	String						link;

	private boolean				draftMode;
}
