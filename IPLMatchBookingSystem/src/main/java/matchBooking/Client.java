package matchBooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {

	public static void main(String[] args) throws Exception {
		//Develop a IPL Match Booking System
		//User should be able to Enroll into the System
		//Admin User Can Add Matches
		//User Should be able to book Tickets
		//User should be able to Search matches by City and Team
		//User should be able to see his past Bookings
		
		//Rules that I have set
		//1)Post Current Date and time -->Match Begin time User should not be able to do booking
		//2)If all Seats are full user should not be able to Book the Tickets
		//3)Handle for MultiThreaded Environment
		
		//My Queries
		//1)If you want the matches that are finished should be Deleted then u must use a job Scheduler
		
		
		
		TickectBookingSystem tickectBookingSystem=new TickectBookingSystem(new ArrayList<User>(),new ArrayList<AdminUser>(),new ArrayList<Match>());
		//User should be able to Enroll into the System
		User u1=new User("1","ram","ram123",new ArrayList<Booking>());
		User u2=new User("2","krishna","krishna123",new ArrayList<Booking>());
		User u3=new User("3","Hanuman","Hanuman123",new ArrayList<Booking>());
		User u4=new User("4","Seetha","Seetha123",new ArrayList<Booking>());
		User u5=new User("5","RadhaRani","RadhaKrishna123",new ArrayList<Booking>());
		List<User> listOfUsers=new ArrayList<>();
		listOfUsers.add(u1);listOfUsers.add(u2);listOfUsers.add(u3);listOfUsers.add(u4);listOfUsers.add(u5);
		tickectBookingSystem.EnrollUsers(listOfUsers);
		
		//Admin User Can Add Matches
		AdminUser a1=new AdminUser("1","Bhrama","b123",RoleType.admin,new ArrayList<Match>());
		AdminUser a2=new AdminUser("2","NadhMuni","n123",RoleType.rmuser,new ArrayList<Match>());
		
		Team t1=new Team("RCB1","RCB",Arrays.asList("Kholi","xyz","rhj"));
		Team t2=new Team("SRH1","SRH",Arrays.asList("abc","xyz","rhj"));
		Team t3=new Team("ChennaiSuperKings1","ChennaiSuperKings",Arrays.asList("Dhoni","xyz","rhj"));
		Team t4=new Team("MumbaiIndians1","MUmbaiIndians",Arrays.asList("Rohit","xyz","rhj"));
		Team t5=new Team("Lucknow1","Lucknow",Arrays.asList("KL Rahul","xyz","rhj"));
		
		List<Team> GameTeams1=new ArrayList<>();
		GameTeams1.add(t1); GameTeams1.add(t2);
		List<Team> GameTeams2=new ArrayList<>();
		GameTeams1.add(t3); GameTeams1.add(t4);
		
		
		LocalDateTime beginingDateTime1=LocalDateTime.of(2024, 3, 28, 10, 00);
		LocalDateTime endingDateTime1=LocalDateTime.of(2024, 3, 28, 11, 00);
		Match m1=new Match("match1","Hydrabad",GameTeams1,new ArrayList<Seat>(),"GachiBowli",beginingDateTime1,endingDateTime1);
		tickectBookingSystem.addMatch(a1,m1);
		
		LocalDateTime beginingDateTime2=LocalDateTime.of(2024, 3, 26, 10, 00);
		LocalDateTime endingDateTime2=LocalDateTime.of(2024, 3, 26, 11, 00);
		Match m2=new Match("match2","Ahmadhabad",GameTeams2,new ArrayList<Seat>(),"ModiStadium",beginingDateTime2,endingDateTime2);
		tickectBookingSystem.addMatch(a1,m2);
		
		//User will try to Search Matchs by City and Team 
		tickectBookingSystem.getMatchesByCityandTeam("Hydrabad", "RCB1");
		//Else will will show user All the Available Matches 
		tickectBookingSystem.getAllValidMatchesInSystem();
		
		//User Should be able to see what all Seats are Available for the Match
		List<Seat> AllAvailableSeatsForm1Match=tickectBookingSystem.getAllAvailableSeatsIntheSystemForMatch(m1);
		
		if(AllAvailableSeatsForm1Match.size()==0) {
			System.out.println("Oops Sorry All Seats For this Match were Occupied");
		}
//		else {
//			String Reply1;
//			Reply1 = tickectBookingSystem.bookTickects(u1, Arrays.asList("1","2","5","6"), "match1");
//			System.out.println(Reply1);
//			String Reply2;
//			Reply2 = tickectBookingSystem.bookTickects(u2, Arrays.asList("1","8","9","34"), "match1");
//			System.out.println(Reply2);
//		}
		else {
			//User Should be able to book Tickets
			
				Runnable r1=new Runnable() {
					@Override
					public void run() {
						String Reply1;
						try {
							Reply1 = tickectBookingSystem.bookTickects(u1, Arrays.asList("1","2","5","6"), "match1");
							System.out.println(Reply1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				};
				Runnable r2=new Runnable() {
					@Override
					public void run() {
					String Reply2;
					try {
						Reply2 = tickectBookingSystem.bookTickects(u2, Arrays.asList("11","6","8","9"), "match1");
						System.out.println(Reply2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					}
				};
				Runnable r3=new Runnable() {
					@Override
					public void run() {
					String Reply2;
					try {
						Reply2 = tickectBookingSystem.bookTickects(u2, Arrays.asList("1","26","27","19"), "match1");
						System.out.println(Reply2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					}
				};
				Thread thread1=new Thread(r1);
				Thread thread2=new Thread(r2);
				Thread thread3=new Thread(r3);
				thread1.start();
				thread2.start();
				thread3.start();
				
				    try {
			            thread1.join();
			            thread2.join();
			            thread3.join();
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
		}
		
		
		
		//User should be able to See his past Bookings
		ArrayList<Booking> AllMyPastBookings1=(ArrayList<Booking>) tickectBookingSystem.getAllMyPastBookings(u1);
		System.out.println("=============================================================");
		AllMyPastBookings1.stream().forEach(i->{System.out.print(i.getMatch().getMatchId()+" "+" "+i.getUser().getUserName()+" \n");i.getSeats().stream().forEach(j->System.out.print("SeatNumbers: "+j.getSeatNumber()+ " status:"+j.getIsBooked()+ " \n"));});
		System.out.println("=============================================================");
		
		//User should be able to See his past Bookings
		ArrayList<Booking> AllMyPastBookings2=(ArrayList<Booking>) tickectBookingSystem.getAllMyPastBookings(u2);
		System.out.println("=============================================================");
		AllMyPastBookings2.stream().forEach(i->{System.out.print(i.getMatch().getMatchId()+" "+" "+i.getUser().getUserName()+" \n");i.getSeats().stream().forEach(j->System.out.print("SeatNumbers: "+j.getSeatNumber()+" status:"+j.getIsBooked()+" \n"));});
		System.out.println("=============================================================");
	}
	
	

}
