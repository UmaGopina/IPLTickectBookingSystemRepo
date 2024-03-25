package matchBooking;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	private String userId;
	private String userName;
	private String password;
	private List<Booking> bookings;
	
	public  String addNewBooking(Booking b) {
			bookings.add(b);
			//make all of these booked Seats as Unavaliable 
			b.getSeats().stream().forEach(i->i.setIsBooked(true));
			return "Booking was Successfull";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(userId, other.userId);
	}
	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}


	
	
}
