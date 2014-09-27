package cinema.model;

public class Ticket {

	private int id;
	private int row;
	private int col;
	private Hour hour;
	private Theater theater;

	public Ticket(int row, int col, Hour hour, Theater theater) {
		super();
		this.row = row;
		this.col = col;
		this.hour = hour;
		this.theater = theater;
	}

	public Ticket(int id, int row, int col, Hour hour, Theater theater) {
		super();
		this.id = id;
		this.row = row;
		this.col = col;
		this.hour = hour;
		this.theater = theater;
	}

	public int getId() {
		return id;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Hour getHour() {
		return hour;
	}

	public Theater getTheater() {
		return theater;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", row=" + row + ", col=" + col + ", hour="
				+ hour + ", theater=" + theater + "]";
	}
	
	

}
