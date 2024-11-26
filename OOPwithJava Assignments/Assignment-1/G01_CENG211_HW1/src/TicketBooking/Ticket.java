package TicketBooking;
public class Ticket {
    private int sectionNumber;
    private int rowNumber;
    private int seatNumber;
    private double price;
    private boolean bookingStatus;

    /*Constructors */
    public Ticket(){
        this.sectionNumber = 0;
        this.rowNumber = 0;
        this.seatNumber = 0;
        this.price = 0.0;
        this.bookingStatus = false;
    }

    public Ticket(int sectionNumber, int seatNumber, int rowNumber, double price){
        if (sectionNumber < 0 || rowNumber < 0 || seatNumber < 0 || price < 0) {
            throw new IllegalArgumentException("Values cannot be negative");
        }
        this.sectionNumber = sectionNumber;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
        this.bookingStatus = false;
    }

    public Ticket(Ticket ticket) {
        if (ticket != null) {
            this.sectionNumber = ticket.sectionNumber;
            this.rowNumber = ticket.rowNumber;
            this.seatNumber = ticket.seatNumber;
            this.price = ticket.price;
            this.bookingStatus = ticket.bookingStatus;
        } else {
            System.err.println("Fatal error: Provided ticket is null.");
            System.exit(1);
        }
    }
    
    /* Converts necessary info in a ready to output state */
    public String toString() {
        return "Section: " + sectionNumber + " Row: " + rowNumber + " Seat: " + seatNumber + " - " + price + " TL";

    }

    /* Returns Ticket booked state */
    public boolean isBooked(){
        return bookingStatus ;
    }

    /* Cancels the booking of a ticket */
    public void cancelBooking() {
        if (isBooked()) {
            bookingStatus = false;
        } else {
            System.out.println("Ticket is not booked");
        }
    }
    /* Books a ticket */
    public boolean bookTicket() {
        boolean ret = false;
        if (!isBooked()) {
            this.bookingStatus = true;
            ret = true;
        } else {
            System.out.println("Ticket is already booked");
        }
        return ret;
    }

    public int getSectionNumber(){
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        if (sectionNumber < 0 && sectionNumber > 3) {
            throw new IllegalArgumentException("not expected Section number");
        }
        this.sectionNumber = sectionNumber;
    }
    
    public int getSeatNumber(){
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        if (seatNumber < 0) {
            throw new IllegalArgumentException("Seat number cannot be negative");
        }
        this.seatNumber = seatNumber;
    }
    
    public int getRowNumber(){
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        if (rowNumber < 0) {
            throw new IllegalArgumentException("Row number cannot be negative");
        }
        this.rowNumber = rowNumber;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    


}
