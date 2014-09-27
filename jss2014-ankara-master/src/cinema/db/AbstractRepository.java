package cinema.db;

import java.sql.Connection;

public abstract class AbstractRepository {

	private Connection connection;

	public AbstractRepository(Connection connection) {
		this.connection = connection;
	}

	protected Connection getConnection() {
		return connection;
	}
	
}
