package FileInOut;

import TicketBooking.Customer;
import TicketBooking.Ticket;
import TicketBooking.Venue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class FileIO {

    // CSV dosyasından müşteri bilgilerini okuyan bir metot
    public static Customer[] loadCustomers(String filePath,Venue venue){
        Customer[] customers = null;
        Ticket ticket; 
        int seat;
        int row;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int customerCount = 0;
            br.readLine();
            // Satırları sayarak müşteri sayısını belirle
            while ((line = br.readLine()) != null) {
                customerCount++;
            }

            // Müşteri dizisini oluştur
            customers = new Customer[customerCount];

            // Dosyayı tekrar başa sar ve müşteri verilerini oku
            br.close();
            BufferedReader br2 = new BufferedReader(new FileReader(filePath));
            int index = 0;
            br2.readLine();
            while ((line = br2.readLine()) != null) {
                // StringTokenizer ile satırı parçala
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                String name = tokenizer.nextToken(); // İlk token: müşteri ismi
                int bookedTickets = Integer.parseInt(tokenizer.nextToken()); // İkinci token: bilet sayısı

                // Customer nesnesi oluştur ve dizine ekle
                Customer customer = new Customer(name);
                int ID = (int)(Math.random() * 4);
                
                // bookTicket sayısını belirtmek için for döngüsüyle boş bilet ekliyoruz
                for (int i = 0; i < bookedTickets; i++) {
                    do {
                        seat = (int)(Math.random() * 60);
                        row = (int)(Math.random() * 10);
                        ticket = venue.getTicket(ID, row, seat); 
                    } while (ticket.isBooked());    
                    if(!venue.bookTicket(ID, row, seat)){
                        System.out.println("Ticket failed to book");
                    }
                    customer.buyTicket(ticket);
                }
                
                customers[index] = customer;
                index++;
            }
            
            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }
}