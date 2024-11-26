package TicketBooking;
public class Customer {
    private String name;
    private int numberOfBookedTickets;
    private Ticket[] bookedTickets;

    /** Constructor */
    public Customer() {
        name = "";
        numberOfBookedTickets = 0;
        bookedTickets = new Ticket[20];
    }

    public Customer(String customerName){
        name = customerName;
        numberOfBookedTickets = 0;
        bookedTickets = new Ticket[20];
    }

    /** Getter */

    public String getName(){
        return name;
    }

    public int getNumberOfBookedTickets(){
        return numberOfBookedTickets;
    }

    /*A function that allows the customer to buy a ticket and add it to the bookedTickets array.
     */
    public void buyTicket(Ticket ticket){
        bookedTickets[numberOfBookedTickets] = ticket;
        numberOfBookedTickets += 1;
    }

    /* A function which prints a customer's name and lists customer's bought tickets */
    public void printTicketInfo(){
        System.out.println(name+"'s Booked Tickets:");
        for (int i = 1;i<numberOfBookedTickets;i++){
            System.out.println("Ticket "+i+": "+bookedTickets[i].toString());
        }
    }
    public double getTotalBookedTicketsPrice(){
        double total = 0;
        for (int i = 0; i < numberOfBookedTickets; i++) {
            total += bookedTickets[i].getPrice();
            }
        return total;
    }
    

}
