package library.filehandling;

import java.io.*;

public class MembersFH implements FileHandler {
    private static final String MEMBER_FILE = "members.txt";

    // Read all lines from members file
    public String[] read() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
            while (br.readLine() != null) {
                count++;
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading members file!");
            return new String[0];
        }
        String[] lines = new String[count];
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
            for (int i = 0; i < count; i++) {
                lines[i] = br.readLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading members file!");
            return new String[0];
        }
        return lines;
    }

    // Overwrite all data to members file
    public void writeAll(String[] data) {
        try (FileWriter file = new FileWriter(MEMBER_FILE)) {
            for (String line : data) {
                file.write(line + "\n");
            }
        } 
        catch (IOException e) {
            System.out.println("Error writing members file!");
        }
    }

    // Append a single line to members file
    public void append(String line) {
        try (FileWriter file = new FileWriter(MEMBER_FILE, true)) {
            file.write(line + "\n");
        } 
        catch (IOException e) {
            System.out.println("Error appending to members file!");
        }
    }
}
