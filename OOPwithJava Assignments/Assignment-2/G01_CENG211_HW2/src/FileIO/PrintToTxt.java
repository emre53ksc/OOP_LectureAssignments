package FileIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PrintToTxt {
    // Method to print a string to a text file
    public static void printToFile(String fileName, String content) {

        // Try-with-resources to ensure the BufferedWriter is closed after use
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // Write the content to the file
            writer.write(content);
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            
            // Handle any IO exceptions that occur
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


   
}




