package matchBooking;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Team {
	private String TeamId;
	private String TeamName;
	private List<String> players;
}
