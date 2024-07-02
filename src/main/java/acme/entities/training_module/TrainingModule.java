
package acme.entities.training_module;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
import acme.roles.Developer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code"), @Index(columnList = "developer_id")
})
public class TrainingModule extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-\\d{3}")
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				creationMoment;

	@NotBlank
	@Length(max = 100)
	private String				details;

	@NotNull
	private DifficultyLevel		difficultyLevel;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				updateMoment;

	@URL
	private String				link;

	@NotNull
	@Min(0)
	private Integer				totalTime;

	private Boolean				draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@ManyToOne(optional = false)
	@Valid
	private Developer			developer;
}
