package matchBooking;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

import matchBooking.payments.*;

@Data
@AllArgsConstructor
public class TickectBookingSystem {
	private List<User> userss;
	private List<AdminUser> adminUsers;
	private List<Match> matchs;
	
	//User should be able to Enroll into the System
	public void  EnrollUsers(List<User> puserss) {
		puserss.forEach(i->userss.add(i));
	}
	//Add Admin Users
	public void  EnrollAdminUsers(List<AdminUser> puserss) {
		puserss.forEach(i->adminUsers.add(i));
	}
	//Admin User Can Add Matches
	public void addMatch(AdminUser pAdminUser,Match match) throws Exception {
		if(pAdminUser.getRoleType().equals(RoleType.admin)) {
			 matchs.add(match);
			 //to maintain By directional Relation ship
			 pAdminUser.addMatches(match);
		}else {
			throw new Exception("Sorry This User is not having Access to add new Matches into the System");
		}
	}
	//User Should be able to book Tickets
	public  String  bookTickects(User user,List<String> requestedSeatNumberes,String matchID) throws Exception {
		Match match= getMatcheByMatchId(matchID);
		List<Seat> requestedSeats=getSeatsforRequestedSeatNumbers(requestedSeatNumberes,match);
		
		for(int i=0;i<=requestedSeats.size()-1;i++) {
			requestedSeats.get(i).getSeatLock().lock();
				if(requestedSeats.get(i).getIsBooked()) {
					//If any One of the Seats are booked Booked then unlock the otherAcquiredLocks in Else condition
					//VERY VERY IMP NOTE:I should not do unlock the If i didn't aquired it...
					for(int j=0;j<=i;j++) {
						requestedSeats.get(j).getSeatLock().unlock();
					}
					return "Sorry user: "+user.getUserName()+" few Seats you are trying to aquire are currently booked...please try with avaiable seats ";
				}
		}
		//If the workflow comes till here means all my Seats are available for Booking and I locked them...lets proceed with booking
		//Calculate the Amount by SeatTypes for now I am putting Dummay value
		Integer totalAmount=1000;
		//Do Payment
		Payment payment=new Payment(null, new CashOnDelivery(), false, totalAmount);
		payment.doPayment(new CashOnDelivery(), totalAmount);
		//check payment was successful or not then add Bookings Else Revert 
		String BookingStatus="Booking was Not Successful";
		if(payment.getPaymentStatus()) {
			//payment was successful Do Booking
			Booking b=new Booking(user,match,requestedSeats,payment);
			BookingStatus=user.addNewBooking(b);
		}
		
		for(int j=0;j<=requestedSeats.size()-1;j++) {
			requestedSeats.get(j).getSeatLock().unlock();
		}
		return BookingStatus;
	}
	
	public List<Seat> getSeatsforRequestedSeatNumbers(List<String> requestedSeatNumberes,Match match) {
		List<Seat> finalList=new ArrayList<Seat>();
		List<Seat> allSeatsInMatch=match.getSeats();
		for(int i=0;i<=allSeatsInMatch.size()-1;i++) {
			for(int j=0;j<=requestedSeatNumberes.size()-1;j++) {
				if(allSeatsInMatch.get(i).getSeatNumber().equalsIgnoreCase(requestedSeatNumberes.get(j))) {
					finalList.add(allSeatsInMatch.get(i));
				}
			}
		}
		return finalList;
	}
	private static List<ArrayList<Seat>> checkIfSeatsAreAvailableForAMatch(List<Seat> s,Match match) {
		ArrayList<Seat> Avaliable_Seats=new ArrayList<Seat>();
		ArrayList<Seat> UnAvaliable_Seats=new ArrayList<Seat>();
		synchronized(TickectBookingSystem.class) {
			//check these particular Seats Available or not
			List<Seat> seats=match.getSeats();
			for(int i=0;i<=s.size()-1;i++) {
				//Check each Seat-Number is Available or not
				//s.get(i).getSeatNumber();
				for(int j=0;j<=seats.size()-1;j++) {
					if(seats.get(j).getSeatNumber().equals(s.get(i).getSeatNumber())) {
						if(!seats.get(i).getIsBooked()) {
							//If isBooked is false 
							Avaliable_Seats.add(seats.get(i));
						}else {
							//If isBooked is false 
							UnAvaliable_Seats.add(seats.get(i));
						}
					}
				}
			}
			List<ArrayList<Seat>> filteredSeatsInfo=new ArrayList<ArrayList<Seat>>();
			filteredSeatsInfo.add(Avaliable_Seats);
			filteredSeatsInfo.add(UnAvaliable_Seats);
			return filteredSeatsInfo;
		}
	}
	public  Match getMatcheByMatchId(String matchID) throws Exception{
		Optional<Match> match=matchs.stream().filter(i->{if(i.getMatchId().equalsIgnoreCase(matchID)) {return true;}else{return false;}}).findFirst();
		if(match.isPresent()) {
			 if(LocalDateTime.now().isBefore(match.get().getMatchEndDateandTime())) {
				 return match.get();
			 }else {
				 System.out.println("My Local Date now:: "+LocalDateTime.now());
				 throw new  Exception("Sorry this Match Date has Passed:"+match.get().getMatchEndDateandTime());
			 }
		}else {
			throw new  Exception("Sorry Match not Found");
		}
	}
	
	//User should be able to Search matches by City and Team
	public List<Match> getMatchesByCityandTeam(String City,String teamId){
		return matchs.stream().
						filter(i->checkMatchAginestCurrentDate(i.getMatchEndDateandTime())).
						filter(i->compareWithCityandTeam(i,City,teamId)).
						collect(Collectors.toList());
	}
	
	public boolean compareWithCityandTeam(Match match,String City,String teamId) {
		if(match.getCity().equalsIgnoreCase(City)) {
			return true;
		}
		List<Team> teams=match.getTeams();
		for(int i=0;i<=match.getTeams().size()-1;i++){
			if(teams.get(i).getTeamId().equalsIgnoreCase(teamId)) {
				return true;
			}
		} 
		return false;
	}
	
	public boolean checkMatchAginestCurrentDate(LocalDateTime endingDate) {
		 if(LocalDateTime.now().isAfter(endingDate)) {
			 return false;
		 }else {
			 return true;
		 }
	}
	
	//User should be able to see his past Bookings
	public List<Booking> getAllMyPastBookings(User user) throws Exception {
		String userId=user.getUserId();
		if(userss.contains(user)) {
			return user.getBookings();
		}else {
			throw new Exception("Sorry User Does Not Exists in the System");
		}
	}
	
	//Get all the Valid Matches in the System
	public List<Match> getAllValidMatchesInSystem() throws Exception {
		return matchs.stream().
				filter(i->checkMatchAginestCurrentDate(i.getMatchEndDateandTime())).
				collect(Collectors.toList());
	}
	//Get all AvailableSeatsInTheSystemForAmatch
	public List<Seat> getAllAvailableSeatsIntheSystemForMatch(Match m1) {
		return m1.getSeats().stream().filter(i->!i.getIsBooked()).collect(Collectors.toList());
	}
	
}
