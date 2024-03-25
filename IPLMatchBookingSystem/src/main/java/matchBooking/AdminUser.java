package matchBooking;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public  class AdminUser {
	private String userId;
	private String userName;
	private String password;
	private RoleType roleType;
	private List<Match> matchs;
	
	public void addMatches(Match match) {
		matchs.add(match);
	}
}
