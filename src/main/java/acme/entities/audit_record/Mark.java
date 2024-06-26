
package acme.entities.audit_record;

public enum Mark {

	A_PLUS("A+"), A("A"), B("B"), C("C"), F("F"), F_MINUS("F-");


	private final String displayValue;


	private Mark(final String displayValue) {
		this.displayValue = displayValue;
	}

	public static Mark parseAuditMark(final String value) {
		for (Mark mark : Mark.values())
			if (mark.displayValue.equals(value))
				return mark;
		return null;
	}

	@Override
	public String toString() {
		return this.displayValue;
	}
}
