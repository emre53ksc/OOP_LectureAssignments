package TicketBooking;

import java.util.Random;

public class Section {
    private int ID;
    private Ticket[][] seats = new Ticket[10][60];
    private double maxPrice;
    private double minPrice;

    public Section() {
        this(0, 0.0, 0.0);
    }

    public Section(Section section) {
        this(section.ID, section.maxPrice, section.minPrice);
        for (Ticket row[] : section.seats) {
            for (Ticket seat : row) {
                this.seats[seat.getRowNumber()][seat.getSeatNumber()] = new Ticket(seat);
            }
        }
    }

    /* Constructors */
    public Section(int givenID, double maxPrice, double minPrice) {
        this.ID = givenID;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    public void initializeSeats() {
        for (int i = 0; i < 60; i++) {
            seats[0][i] = new Ticket(this.ID, i, 0, maxPrice);
        }
        for (int i = 0; i < 60; i++) {
            seats[1][i] = new Ticket(this.ID, i, 1, maxPrice * 0.8);
        }
        for (int i = 2; i < 10; i++) {
            for (int j = 0; j < 60; j++) {
                seats[i][j] = new Ticket(this.ID, j, i, setPrice());
            }
        }
    }

    public double setPrice() {
        Random rand = new Random();
        return minPrice + (maxPrice - minPrice) * rand.nextDouble();
    }

    public boolean bookTicket(int rowNumber, int seatNumber) {
        return seats[rowNumber][seatNumber].bookTicket();
    }

    public Ticket getTicketByRowSeat(int rowNumber, int seatNumber) {
        return new Ticket(seats[rowNumber][seatNumber]);
    }

    public double getTotalRevenue() {
        double totalRevenue = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 60; j++) {
                if (seats[i][j].isBooked()) {
                    totalRevenue += seats[i][j].getPrice();
                }
            }
        }
        return totalRevenue;
    }

    public void showSeatOccupancies() {
        System.out.println("Section " + ID);
        for (int i = 0; i < 10; i++) {
            System.out.println();
            for (int j = 0; j < 60; j++) {

                if (seats[i][j].isBooked()) {
                    System.out.print(" X");
                } else {
                    System.out.print(" O");
                }
            }
        }
        System.out.println("\n");
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
}
