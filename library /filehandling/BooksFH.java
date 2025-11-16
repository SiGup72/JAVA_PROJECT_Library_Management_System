package library.filehandling;

import java.io.*;

public class BooksFH implements FileHandler {
    private static final String BOOK_FILE = "books.txt";

    // Read all lines from books file
    public String[] read() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(BOOK_FILE))) {
            while (br.readLine() != null) {
                count++;
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading books file!");
            return new String[0];
        }
        String[] lines = new String[count];
        try (BufferedReader br = new BufferedReader(new FileReader(BOOK_FILE))) {
            for (int i = 0; i < count; i++) {
                lines[i] = br.readLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading books file!");
            return new String[0];
        }
        return lines;
    }

    // Overwrite all data to books file
    public void writeAll(String[] data) {
        try (FileWriter file = new FileWriter(BOOK_FILE)) {
            for (String line : data) {
                file.write(line + "\n");
            }
        }
        catch (IOException e) {
            System.out.println("Error writing to books file!");
        }
    }

    // Append a single line to books file
    public void append(String line) {
        try (FileWriter file = new FileWriter(BOOK_FILE, true)) {
            file.write(line + "\n");
        } 
        catch (IOException e) {
            System.out.println("Error appending to books file!");
        }
    }
}
