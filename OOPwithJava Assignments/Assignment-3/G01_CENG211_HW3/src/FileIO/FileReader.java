package FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;



public class FileReader {    
    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param fileName the name of the file to read
     * @return the content of the file as a string
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if an I/O error occurs
     */
    public String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        File file = new File(fileName);
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
        }
        
        return content.toString();
    }
}
