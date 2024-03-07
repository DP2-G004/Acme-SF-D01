
package acme.datatypes;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.client.data.AbstractDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Period extends AbstractDatatype {

	@NotNull
	@Past
	Date	startDate;

	@NotNull
	Date	endDate;


	public Period(final Date start, final Date end) {
		if (!start.before(end))
			throw new IllegalArgumentException("The start date must be before the end date");
		this.startDate = start;
		this.endDate = end;
	}

}
