package matchBooking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Match {
	private String matchId;
	private String city;
	private List<Team> teams;
	List<Seat> seats;
	private String stadium;
	private LocalDateTime matchBegDateandTime;
	private LocalDateTime matchEndDateandTime;
	//ArrayLocks for Seats
	private  Lock[] seatLocks;
	
	public Match(String matchId, String city, List<Team> teams, List<Seat> pseats, String stadium,
			LocalDateTime matchBegDateandTime, LocalDateTime matchEndDateandTime) {
		super();
		this.matchId = matchId;
		this.city = city;
		this.teams = teams;
		this.seats = pseats;
		this.stadium = stadium;
		this.matchBegDateandTime = matchBegDateandTime;
		this.matchEndDateandTime = matchEndDateandTime;
		//Allocate Seats
		setSeats(seats);
	}


	public static void setSeats(List<Seat> TotalSeatsForMatchs){
		//FrontRow Seats
		for(Integer i=1;i<=10;i++) {
			TotalSeatsForMatchs.add(new Seat(SeatTypes.FrontRow,i.toString(),false,new ReentrantLock(true)));
		}
		//FirstClass Seats
		for(Integer i=11;i<=20;i++) {
			TotalSeatsForMatchs.add(new Seat(SeatTypes.FirstClass,i.toString(),false,new ReentrantLock(true)));
		}
		//Premium Seats
		for(Integer i=21;i<=30;i++) {
			TotalSeatsForMatchs.add(new Seat(SeatTypes.Premium,i.toString(),false,new ReentrantLock(true)));
		}
		//HighRise Seats
		for(Integer i=31;i<=40;i++) {
			TotalSeatsForMatchs.add(new Seat(SeatTypes.HighRise,i.toString(),false,new ReentrantLock(true)));
		}
	}
	
	
	
}
