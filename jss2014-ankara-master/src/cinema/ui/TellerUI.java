package cinema.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cinema.db.TheaterRepository;
import cinema.db.TicketRepository;
import cinema.model.Hour;
import cinema.model.Theater;
import cinema.model.Ticket;

public class TellerUI extends AbstractUI{

	private Map<Integer, Theater> theaters = new HashMap<>();
	private Collection<Ticket> tickets = new ArrayList<>();
	private final TicketRepository ticketRepository;
	private final TheaterRepository theaterRepository;
	
	public TellerUI() {
		try {
			theaterRepository = new TheaterRepository(getConnection());
			ticketRepository = new TicketRepository(getConnection(), theaterRepository);
			Collection<Theater> theaterList = theaterRepository.getAll();
			for (Theater theater : theaterList) {
				theaters.put(theater.getId(), theater);
			}
			tickets = ticketRepository.getAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		TellerUI ui = new TellerUI();
		ui.runMainLoop();
	}

	@Override
	protected void showMenu() {
		System.out.println();
		System.out.println("Please make a selection:");
		System.out.println("1. List theatres");
		System.out.println("2. Sell ticket");
		System.out.println("q. Quit");
	}

	@Override
	protected void runMainLoop() {
		Scanner scanner = new Scanner(System.in);
		String line = "";
		
		theaters.put(1, new Theater(1, "Salon A", 5, 5));
		theaters.put(2, new Theater(2, "Salon B", 4, 5));
		theaters.put(3, new Theater(3, "Salon C", 5, 8));
		
		while(!(line = showMenuAndGetSelection(scanner)).equals("q")){
			int selection = Integer.parseInt(line);
			switch (selection) {
			case 1:
				listTheaters(theaters.values());
				break;
			case 2:
				sellTickets(scanner, theaters, tickets);
				break;

			default:
				System.out.println("Invalid choice, please try again.");
				break;
			}
		}
	}
	
	private void sellTickets(Scanner scanner, 
			Map<Integer, Theater> theaters, Collection<Ticket> tickets){
		System.out.println("Select a theater:");
		listTheaters(theaters.values());
		Integer selectedTheaterId = Integer.valueOf(scanner.nextLine());
		Theater selectedTheater = theaters.get(selectedTheaterId);
		if(selectedTheater==null){
			System.out.println("Wrong selection");
			return;
		}
		System.out.println("Select an hour:");
		listAllHours();
		Integer selectedHourIndex = Integer.valueOf(scanner.nextLine());
		Hour selectedHour = Hour.values()[selectedHourIndex-1];
		System.out.println("Enter number of tickets:");
		Integer numPeople = Integer.valueOf(scanner.nextLine());
		int availableSpace = selectedTheater.getAvailableSpace(tickets, selectedHour);
		if(availableSpace<numPeople){
			System.out.println("No room for "+numPeople+" people!");
			return;
		}
		Collection<Ticket> contiguousSeats = selectedTheater.getContiguousSeats(tickets, selectedHour, numPeople);
		if(contiguousSeats.size()==numPeople){
			for (Ticket ticket : contiguousSeats) {
				tickets.add(ticketRepository.save(ticket));
			}
			System.out.println(contiguousSeats);
			System.out.println("Tickets created.");
			showSeatStatus(selectedTheater.getEmptySeats(tickets, selectedHour));
			return;
		}
		System.out.println("No contiguous seats available, do you want to continue?(y/n)");
		String yesOrNo = scanner.nextLine();
		if(yesOrNo.equals("n")){
			return;
		}
		Collection<Ticket> seats = selectedTheater.getSeats(tickets, selectedHour, numPeople);
		for (Ticket ticket : seats) {
			tickets.add(ticketRepository.save(ticket));
		}
		System.out.println(seats);
		System.out.println("Tickets created.");
		showSeatStatus(selectedTheater.getEmptySeats(tickets, selectedHour));
	}
	
	private void listAllHours(){
		int i = 1;
		for (Hour hour : Hour.values()) {
			System.out.println(i+": "+hour.getFormattedHour()+", Price: $"+hour.getPrice());
			i++;
		}
	}
	
	private void showSeatStatus(boolean[][] seats){
		for (int i = 0; i < seats.length; i++) {
			boolean[] currentRow = seats[i];
			for (int j = 0; j < currentRow.length; j++) {
				boolean currentSeat = currentRow[j];
				if(currentSeat){
					System.out.print("# ");
				}else{
					System.out.print("☺ ");
				}
			}
			System.out.println();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
