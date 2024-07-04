
package acme.entities.invoice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.sponsorship.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				registrationTime;

	@Temporal(TemporalType.DATE)
	@Past
	private Date				dueDate;

	@NotNull
	private Money				quantity;

	@NotNull
	@Min(0)
	private Double				tax;

	@URL
	private String				link;

	private Boolean				draftMode;


	@Transient
	public Money getTotalAmount() {
		Double totalAmount;
		totalAmount = this.quantity.getAmount() + this.quantity.getAmount() * (this.tax / 100);

		Money res = new Money();
		res.setAmount(totalAmount);
		res.setCurrency(this.quantity.getCurrency());

		return res;
	}

	//Relationships


	@Valid
	@ManyToOne(optional = false)
	@NotNull
	private Sponsorship sponsorship;

}
