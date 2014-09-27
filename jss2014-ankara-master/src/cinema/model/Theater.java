package cinema.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Theater {
	
	private int id;
	private String name;
	private int rows;
	private int cols;
	
	public Theater(String name, int rows, int cols) {
		super();
		this.name = name;
		this.rows = rows;
		this.cols = cols;
	}
	
	public Theater(int id, String name, int rows, int cols) {
		super();
		this.id = id;
		this.name = name;
		this.rows = rows;
		this.cols = cols;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	
	public Collection<Ticket> getSeats(Collection<Ticket> tickets, Hour hour, int numPeople){
		boolean[][] emptySeats = getEmptySeats(tickets, hour);
		Collection<Ticket> result = new ArrayList<>();
		for (int i = 0; i < emptySeats.length; i++) {
			boolean[] currentRow = emptySeats[i];
			for (int j = 0; j < currentRow.length; j++) {
				boolean currentSeat = currentRow[j];
				if(currentSeat){
					result.add(new Ticket(i+1, j+1, hour, this));
				}
				if(result.size()==numPeople){
					return result;
				}
			}
		}
		return result;
	}
	
	public Collection<Ticket> getContiguousSeats(Collection<Ticket> tickets, Hour hour, int numPeople){
		boolean[][] emptySeats = getEmptySeats(tickets, hour);
		int counter = 0;
		Collection<Ticket> result = new ArrayList<>();
		for (int i = 0; i < emptySeats.length; i++) {
			counter = 0;
			boolean[] currentRow = emptySeats[i];
			for (int j = 0; j < currentRow.length; j++) {
				if(currentRow[j]){
					counter++;
				}else{
					counter = 0;
				}
				if(counter == numPeople){
					for (int k = 0; k < numPeople; k++) {
						result.add(new Ticket(i+1, j+1-k, hour, this));
					}
					return result;
				}
			}
		}
		return result;
	}
	
	public int getAvailableSpace(Collection<Ticket> tickets, Hour hour){
		boolean[][] emptySeats = getEmptySeats(tickets, hour);
		int availableSpace = 0;
		for (int i = 0; i < emptySeats.length; i++) {
			for (int j = 0; j < emptySeats[i].length; j++) {
				if(emptySeats[i][j]){
					availableSpace++;
				}
			}
		}
		return availableSpace;
	}
	
	public boolean[][] getEmptySeats(Collection<Ticket> tickets, Hour hour){
		boolean[][] emptySeats = new boolean[rows][cols];
		for (int i = 0; i < emptySeats.length; i++) {
			boolean[] currentRow = emptySeats[i];
			Arrays.fill(currentRow, true);
		}
		for (Ticket ticket : tickets) {
			if(ticket.getTheater().equals(this) && ticket.getHour().equals(hour)){
				emptySeats[ticket.getRow()-1][ticket.getCol()-1] = false;
			}
		}
		return emptySeats;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Theater)){
			return false;
		}
		return this.getId() == ((Theater)other).getId();
	}
	
	@Override
	public int hashCode() {
		return this.getId();
	}
	

}
