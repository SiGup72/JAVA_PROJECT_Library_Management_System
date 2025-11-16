package library.filehandling;

import java.io.*;

public class WishlistFH implements FileHandler {
    private static final String WISHLIST_FILE = "wishlist.txt";

    // Read all lines from wishlist file
    public String[] read() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(WISHLIST_FILE))) {
            while (br.readLine() != null) {
                count++;
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading wishlist file!");
            return new String[0];
        }
        String[] lines = new String[count];
        try (BufferedReader br = new BufferedReader(new FileReader(WISHLIST_FILE))) {
            for (int i = 0; i < count; i++) {
                lines[i] = br.readLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading wishlist file!");
            return new String[0];
        }
        return lines;
    }

    // Overwrite all data to wishlist file
    public void writeAll(String[] data) {
        try (FileWriter file = new FileWriter(WISHLIST_FILE)) {
            for (String line : data) {
                file.write(line + "\n");
            }
        } 
        catch (IOException e) {
            System.out.println("Error writing wishlist file!");
        }
    }

    // Append a single line to wishlist file
    public void append(String line) {
        try (FileWriter file = new FileWriter(WISHLIST_FILE, true)) {
            file.write(line + "\n");
        } 
        catch (IOException e) {
            System.out.println("Error adding to wishlist file!");
        }
    }
}
