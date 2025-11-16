package library.services;

import library.models.*;
import library.filehandling.*;
import java.util.*;
import java.time.LocalDate;

public class LibrarianService implements LibraryService {

    private MembersFH membersFH = new MembersFH();
    private BooksFH booksFH = new BooksFH();
    private TransactionsFH transactionsFH = new TransactionsFH();
 
    public void viewAllBooks() {
        String[] books = booksFH.read();   // read all book lines from file

        if (books.length == 0) {
            System.out.println("No books available!");
            return;
        }

        System.out.println("=== AVAILABLE BOOKS ===");

        // Loop through every book record
        for (String line : books) {
            if (line == null || line.trim().isEmpty()) continue;  // skip empty lines

            String[] p = line.split(","); // p[0]=id, p[1]=title, p[2]=author, p[3]=genre
            if (p.length < 4) continue;    // skip malformed lines

            // Create Book object and display
            Book book = new Book(p[0], p[1], p[2], p[3]);
            book.display();
            System.out.println("------------------");
        }
    }

    
    public boolean viewIssuedBooks(String memberID) {
        String[] trans = transactionsFH.read(); // read all transaction records
        boolean found = false;

        System.out.println("=== BOOKS CURRENTLY ISSUED TO YOU ===");

        for (String line : trans) {
            String[] t = line.split(",");  // t[1]=bookID, t[2]=memberID, t[5]=returned?
            if (t.length < 6) continue;

            // Check if book belongs to member AND not returned yet
            if (t[2].equalsIgnoreCase(memberID) && t[5].equals("false")) {
                System.out.println("Book ID: " + t[1] + " | Issued on: " + t[3]);
                found = true;
            }
        }

        return found;  // tells main() if issued books exist or not
    }


    public void viewAllMembers() {
        String[] members = membersFH.read();
        if (members.length == 0) {
            System.out.println("No members found!");
            return;
        }

        Member[] m = new Member[members.length];

        // Convert CSV → Member objects
        for (int i = 0; i < members.length; i++) {
            String[] p = members[i].split(",");

            if (p.length >= 4) {
                LocalDate joinDate = LocalDate.parse(p[3]);
                m[i] = new Member(p[0], p[1], p[2], joinDate);
            }
        }

        System.out.println("=== REGISTERED MEMBERS ===");
        for (Member member : m) {
            if (member != null) {
                member.display();
                System.out.println("------------------");
            }
        }
    }

    //overriding
    public void issueBook(String memberID, String bookID) {

        //Check if book exists
        for (String line : booksFH.read()) {
            String[] p = line.split(",");
            if (p[0].equalsIgnoreCase(bookID)) {

                //Check if book already issued to same member
                for (String tr : transactionsFH.read()) {
                    String[] t = tr.split(",");
                    if (t[1].equalsIgnoreCase(bookID) &&
                        t[2].equalsIgnoreCase(memberID) &&
                        t[5].equalsIgnoreCase("false")) {

                        System.out.println("This book is already issued to this member!");
                        return;
                    }
                }

                //Create a new transaction entry
                String transactionId = "T" + System.currentTimeMillis();
                String entry = transactionId + "," + bookID + "," + memberID + "," +
                               LocalDate.now() + ",null,false,0.0";

                transactionsFH.append(entry); // save to file

                System.out.println("Book issued successfully!");
                return;
            }
        }

        System.out.println("Book not found!");
    }

    //overriding
    public void returnBook(String memberID, String bookID) {

        String[] transactions = transactionsFH.read();
        boolean found = false;

        for (int i = 0; i < transactions.length; i++) {
            String[] t = transactions[i].split(",");

            if (t.length < 6) continue;

            // Find the active transaction
            if (t[1].equalsIgnoreCase(bookID) &&
                t[2].equalsIgnoreCase(memberID) &&
                t[5].equalsIgnoreCase("false")) {

                found = true;

                // Fine calculation (Rs. 5 per day after 14 days)
                LocalDate issueDate = LocalDate.parse(t[3]);
                LocalDate returnDate = LocalDate.now();
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(issueDate, returnDate);
                double fine = Math.max(0, (daysBetween - 14) * 5);

                // Update transaction details
                t[4] = returnDate.toString();  // return date
                t[5] = "true";                 // returned
                t[6] = String.valueOf(fine);   // fine

                transactions[i] = String.join(",", t);

                transactionsFH.writeAll(transactions); // save updates

                System.out.println("Book returned successfully!");
                System.out.println("Fine: Rs." + fine);
                return;
            }
        }

        System.out.println("No active transaction found for this book and member!");
    }


    public void addBook(Book book) {   //Add new book tobooks.txt
        String line = book.getBookId() + "," +  book.getTitle() + "," + book.getAuthor() + "," + book.getGenre();
        booksFH.append(line);
        System.out.println("Book added successfully!");
    }


    public void addMember(Member member) {  //add new member to members.txt
        String line = member.getId() + "," +  member.getName() + "," + member.getEmail() + "," + member.getJoinDate();
        membersFH.append(line);
        System.out.println("Member added successfully!");
    }


    //overriding
    public void searchBook(String keyword) {
        String[] books = booksFH.read();
        boolean found = false;

        if (books == null || books.length == 0) {
            System.out.println("No books available!");
            return;
        }

        // Match keyword in title or author
        for (String line : books) {
            if (line == null || line.trim().isEmpty()) continue;

            String[] p = line.split(",");
            if (p.length < 4) continue;

            // Trim spaces
            for (int i = 0; i < p.length; i++) {
                p[i] = p[i].trim();
            }

            if (p[1].toLowerCase().contains(keyword.toLowerCase()) ||
                p[2].toLowerCase().contains(keyword.toLowerCase())) {

                found = true;

                System.out.println("BookID: " + p[0] + " | Title: " + p[1] + " | Author: " + p[2] + " | Genre: " + p[3]);
                System.out.println("------------------");
            }
        }

        if (!found) {
            System.out.println("No book found for: " + keyword);
        }
    }


    public void viewMembers() {
        Scanner scan = new Scanner(System.in);

        System.out.println("1. All Members");
        System.out.println("2. Specific Member");
        System.out.print("Enter choice: ");

        int choice = Integer.parseInt(scan.nextLine());
        String[] members = membersFH.read();

        if (members.length == 0) {
            System.out.println("No members found!");
            return;
        }

        if (choice == 1) {
            // Show all
            viewAllMembers();
        }
        else if (choice == 2) {
            // Find specific member
            System.out.print("Enter Member ID: ");
            String memberID = scan.nextLine();

            boolean found = false;

            for (String line : members) {
                String[] p = line.split(",");

                if (p.length >= 4 && p[0].equalsIgnoreCase(memberID)) {
                    System.out.println("ID: " + p[0] +
                                       ", Name: " + p[1] +
                                       ", Email: " + p[2] +
                                       ", Join Date: " + p[3]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Member not found with ID: " + memberID);
            }
        }
        else {
            System.out.println("Invalid choice!");
        }
    }


    //overriding
    public void viewTransactions(String memberID) {

        String[] trans = transactionsFH.read();
        boolean found = false;

        for (String line : trans) {
            String[] p = line.split(",");

            if (p.length < 7) continue;

            LocalDate issueDate = LocalDate.parse(p[3]);
            LocalDate returnDate = p[4].equals("null") ? null : LocalDate.parse(p[4]);

            boolean isReturned = Boolean.parseBoolean(p[5]);
            double fineAmount = Double.parseDouble(p[6]);

            Transaction transaction =
                new Transaction(p[0], p[1], p[2], issueDate, returnDate, isReturned, fineAmount);

            // memberID empty - show all
            if (memberID == null || memberID.isEmpty() ||
                transaction.getMemberId().equalsIgnoreCase(memberID)) {

                found = true;
                transaction.display();
                System.out.println("------------------");
            }
        }

        if (!found) {
            if (memberID == null || memberID.isEmpty()) {
                System.out.println("No transactions found!");
            } else {
                System.out.println("No transactions found for member ID: " + memberID);
            }
        }
    }
}
