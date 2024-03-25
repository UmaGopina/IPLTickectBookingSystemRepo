package matchBooking;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Seat {	
	private SeatTypes seatTypes;
	private String seatNumber;
	private Boolean isBooked;
	
	private Lock seatLock; 
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seat other = (Seat) obj;
		return Objects.equals(seatNumber, other.seatNumber);
	}
	@Override
	public int hashCode() {
		return Objects.hash(seatNumber);
	}
	
	
}
