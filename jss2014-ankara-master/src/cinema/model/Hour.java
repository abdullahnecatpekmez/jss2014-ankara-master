package cinema.model;

public enum Hour {
	TEN(10, 5),
	TWELVE(12, 7),
	FOURTEEN(14, 7),
	SIXTEEN(16, 7),
	EIGHTEEN(18, 12),
	TWENTY(20, 12),
	TWENTYTWO(22, 12),
	TWENTYFOUR(24, 10);
	
	private int hour;
	private int price;
	
	private Hour(int hour, int price){
		this.hour = hour;
		this.price = price;
	}

	public int getHour() {
		return hour;
	}

	public int getPrice() {
		return price;
	}
	
	public String getFormattedHour(){
		return getHour()+":00";
	}

}
