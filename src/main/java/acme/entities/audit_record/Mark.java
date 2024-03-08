
package acme.entities.audit_record;

public enum Mark {

	A_PLUS("A+"), A("A"), B("B"), C("C"), F("F"), F_MINUS("F-");


	private final String displayValue;


	private Mark(final String displayValue) {
		this.displayValue = displayValue;
	}

	@Override
	public String toString() {
		return this.displayValue;
	}
}
