package matchBooking;

public enum SeatTypes {
	FrontRow(100),HighRise(200),Premium(1000),FirstClass(500);
	private final Integer Amount;
	SeatTypes(Integer Amount) {
		this.Amount=Amount;
	}
	public Integer getAmount() {
		return Amount;
	}
	
}
