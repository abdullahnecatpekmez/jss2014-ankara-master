package cinema.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import cinema.model.Hour;
import cinema.model.Theater;
import cinema.model.Ticket;


public class TicketRepository extends AbstractRepository{

	private final PreparedStatement getAllStatement;
	private final PreparedStatement saveStatement;
	private final TheaterRepository theaterRepository;

	
	public TicketRepository(Connection connection, TheaterRepository theaterRepository) {
		super(connection);
		this.theaterRepository = theaterRepository;
		try {
			getAllStatement = connection.prepareStatement("SELECT * FROM tickets");
			saveStatement = connection.prepareStatement(
							"INSERT INTO tickets(hour, \"hallId\", row, col) "
							+ "VALUES (?, ?, ?, ?);",
							Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Collection<Ticket> getAll() {
		try {
			ResultSet rs = getAllStatement.executeQuery();
			Collection<Theater> theaters = theaterRepository.getAll();
			Map<Integer, Theater> theaterMap = new HashMap<Integer, Theater>();
			for (Theater theater : theaters) {
				theaterMap.put(theater.getId(), theater);
			}
			List<Ticket> tickets = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				Theater theater = theaterMap.get(rs.getInt("hallId"));
				Hour hour = Hour.values()[rs.getInt("hour")];
				int row = rs.getInt("row");
				int col = rs.getInt("col");
				Ticket ticket = new Ticket(id, row, col, hour, theater);
				tickets.add(ticket);
			}
			return tickets;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Ticket save(Ticket ticket) {
		try {
			Hour hour = ticket.getHour();
			saveStatement.setInt(1, getIndexOfHour(hour));
			Theater theater = ticket.getTheater();
			saveStatement.setInt(2, theater.getId());
			int row = ticket.getRow();
			saveStatement.setInt(3, row);
			int col = ticket.getCol();
			saveStatement.setInt(4, col);
			saveStatement.executeUpdate();

			ResultSet generatedKeys = saveStatement.getGeneratedKeys();
			Ticket result = null;
			while (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				result = new Ticket(id, row, col, hour, theater);
			}
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private int getIndexOfHour(Hour hour) {
		Hour[] values = Hour.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i] == hour) {
				return i;
			}
		}
		return -1;
	}
	

}
