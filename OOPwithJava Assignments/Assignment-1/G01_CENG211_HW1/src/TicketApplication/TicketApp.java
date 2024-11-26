package TicketApplication;
import TicketBooking.Customer;
import TicketBooking.Query;

public class TicketApp {
    public static void main(String[] args) {
        Query query = new Query();
        query.createVenue();
        Customer[] customers = query.readData();
        System.out.println("Section "+(int)query.findHighestRevenueSection()+" is the highest revenue section");
        System.out.println(query.totalRevenue()+" is the total revenue");
        System.out.println(query.occupancyRate()+"% is the occupancy rate");
        query.highestCustomer(customers).printTicketInfo(); 
        System.out.println(query.mostExpensiveTicket().toString()+" is the most expensive ticket");
        
        for(int i = 0; i < 4; i++){
            query.printSections(i);
        }


    }
    
}
