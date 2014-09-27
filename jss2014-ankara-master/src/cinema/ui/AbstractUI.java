package cinema.ui;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Scanner;

import cinema.model.Theater;

public abstract class AbstractUI {
	private final String url = "jdbc:postgresql://localhost/cinema"; 
	private final String user = "postgres";
	private final String password = "1234";
	protected void listTheaters(Collection<Theater> theaters){
		for (Theater theater : theaters) {
			System.out.println(theater.getId()+": "+theater.getName()+", "+theater.getRows()+"x"+theater.getCols());
		}
	}
	
	protected String showMenuAndGetSelection(Scanner scanner){
		showMenu();
		return scanner.nextLine();
	}

	protected abstract void showMenu();
	protected abstract void runMainLoop();
	
	protected Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver loaded.");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		return DriverManager.getConnection(url, user, password);
	}
}
