package matchBooking;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import matchBooking.payments.Payment;

@Data
@AllArgsConstructor
public class Booking {
	private User user;
	private Match match;
	private List<Seat> seats;
	private Payment payment;
	@Override
	public String toString() {
		return "Booking [user=" + user + ", match=" + match + ", seats=" + seats + "]";
	}
	
}
