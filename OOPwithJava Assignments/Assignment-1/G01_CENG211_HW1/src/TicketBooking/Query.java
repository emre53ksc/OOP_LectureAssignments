package TicketBooking;

import FileInOut.FileIO;

public class Query {
    private Venue venue;

    public Query() {
        venue = null;
    }

    public Venue createVenue() {
        venue = new Venue();
        return venue;
    }

    public Customer[] readData() {
        String filePath = "customers.csv";
        return FileIO.loadCustomers(filePath, venue);
    }

    public int findHighestRevenueSection() {
        double highestRevenue = 0.0;
        int highestRevenueSection = 0;

        for (int ID = 0; ID < 4; ID += 1) {
            if (venue.getSection(ID).getTotalRevenue() > highestRevenue) {
                highestRevenueSection = ID;
                highestRevenue = venue.getSection(ID).getTotalRevenue();
            }
        }
        return highestRevenueSection;
    }

    public double totalRevenue() {
        double total = 0;
        for(int i=0; i < 4; i++ ){
            total +=venue.getSection(i).getTotalRevenue();
        }
        return total;
    }

    public double occupancyRate() {
        int totalSeats = 2400;
        int bookedSeats = 0;

        for (int section = 0; section < 4; section++) {
            for (int row = 0; row < 10; row++) {
                for (int seat = 0; seat < 60; seat++) {
                    Ticket ticket = venue.getSection(section).getTicketByRowSeat(row, seat);
                    if (ticket.isBooked()) {
                        bookedSeats++;
                    }
                }
            }
        }
        return (double) bookedSeats / totalSeats * 100;
    }

    public Customer highestCustomer(Customer[] customers) {
        Customer highestPayingCustomer = null;
        double highestTotalPrice = 0;

        for (Customer customer : customers) {
            double totalPrice = customer.getTotalBookedTicketsPrice();

            if (totalPrice > highestTotalPrice) {
                highestTotalPrice = totalPrice;
                highestPayingCustomer = customer;
            }
        }

        return highestPayingCustomer;
    }

    public Ticket mostExpensiveTicket() {
        Ticket mostExpensive = null;
        double highestPrice = 0;

        for(int ID = 0; ID < 4; ID += 1) {
            for (int row = 0; row < 10; row += 1) {
                for (int seat = 0; seat < 60; seat += 1) {
                    Ticket ticket = venue.getTicket(ID, row, seat);
                    if (ticket.getPrice() > highestPrice) {
                        highestPrice = ticket.getPrice();
                        mostExpensive = ticket;
                    }
                }
            }
        }

        return mostExpensive;
    }

    public void printSections(int sectionNumber) {
        venue.getSection(sectionNumber).showSeatOccupancies();
    }

}
