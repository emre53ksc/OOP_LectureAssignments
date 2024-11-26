package TicketBooking;
public class Venue {
    private Section[] sections = new Section[4];

    // Constructor
    public Venue() {
        for (int i = 0; i<4; i += 1) {
            int max1 = 4000-i*500;
            int min1 = 5000-i*500;
            int max2 = 3000-i*500;
            int min2 = 4000-i*500;
            sections[i] = new Section(i,(Math.random() * (max1 - min1 + 1) + min1),(Math.random() * (max2 - min2 + 1) + min2));
            sections[i].initializeSeats();
        }
    }
    public Section getSection(int Number){
        return new Section(sections[Number]);
    }

    public Ticket getTicket(int sectionNumber, int rowNumber, int seatNumber){
        return sections[sectionNumber].getTicketByRowSeat(rowNumber, seatNumber);
        
    }

    public boolean bookTicket(int sectionNumber, int rowNumber, int seatNumber){
        return sections[sectionNumber].bookTicket(rowNumber, seatNumber);
    }
}
