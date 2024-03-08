
package acme.entities.audit_record;

public enum Mark {

	Apos {

		@Override
		public String toString() {
			return "A+";
		}
	},
	A, B, C, F {

	},
	Fneg {

		@Override
		public String toString() {
			return "F-";
		}
	};


	@Override
	public String toString() {
		return super.toString();
	}
}
