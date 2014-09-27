package cinema.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import cinema.model.Theater;

public class TheaterRepository extends AbstractRepository{

	private final PreparedStatement getAllStatement;
	private final PreparedStatement saveStatement;

	public TheaterRepository(Connection connection) throws SQLException {
		super(connection);
		getAllStatement = connection.prepareStatement("SELECT * FROM halls");
		saveStatement = connection.prepareStatement("INSERT INTO "
				+ "halls(rows, cols, name) "
				+ "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
	}
	
	public Collection<Theater> getAll(){
		try {
			ResultSet rs = getAllStatement.executeQuery();
			Collection<Theater> result = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				int rows = rs.getInt("rows");
				int cols = rs.getInt("cols");
				String name = rs.getString("name");
				Theater theater = new Theater(id, name, rows, cols);
				result.add(theater);
			}
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Theater saveTheater(Theater theater){
		try {
			saveStatement.setInt(1, theater.getRows());
			saveStatement.setInt(2, theater.getCols());
			saveStatement.setString(3, theater.getName());
			saveStatement.executeUpdate();
			
			ResultSet generatedKeys = saveStatement.getGeneratedKeys();
			Theater result = null;
			while (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				result = new Theater(id, theater.getName(), 
						theater.getRows(), theater.getCols());
			}
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
