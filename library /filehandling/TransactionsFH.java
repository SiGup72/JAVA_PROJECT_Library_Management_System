package library.filehandling;

import java.io.*;

public class TransactionsFH implements FileHandler {
    private static final String TRANSACTION_FILE = "transactions.txt";

    // Read all lines from transactions file
    public String[] read() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            while (br.readLine() != null) {
                count++;
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading transactions file!");
            return new String[0];
        }
        String[] lines = new String[count];
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            for (int i = 0; i < count; i++) {
                lines[i] = br.readLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading transactions file!");
            return new String[0];
        }
        return lines;
    }

    // Overwrite all data to transactions file
    public void writeAll(String[] data) {
        try (FileWriter file = new FileWriter(TRANSACTION_FILE)) {
            for (String line : data) {
                file.write(line + "\n");
            }
        } 
        catch (IOException e) {
            System.out.println("Error writing transactions file!");
        }
    }

    // Append a single line to transactions file
    public void append(String line) {
        try (FileWriter file = new FileWriter(TRANSACTION_FILE, true)) {
            file.write(line + "\n");
        } 
        catch (IOException e) {
            System.out.println("Error adding transaction!");
        }
    }
}
