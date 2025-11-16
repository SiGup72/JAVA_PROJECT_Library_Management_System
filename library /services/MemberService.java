package library.services;

import library.models.*;
import library.filehandling.*;
import java.util.*;
import java.time.LocalDate;

public class MemberService implements LibraryService {

    // File handlers → to read/write data from txt files
    private MembersFH membersFH = new MembersFH();
    private BooksFH booksFH = new BooksFH();
    private TransactionsFH transactionsFH = new TransactionsFH();
    private WishlistFH wishlistFH = new WishlistFH();

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

    //overriding
    public void searchBook(String keyword) {
        String[] books = booksFH.read();
        boolean found = false;

        if (books.length == 0) {
            System.out.println("No books available!");
            return;
        }

        // Loop through books and match keyword
        for (String line : books) {
            if (line == null || line.trim().isEmpty()) continue;

            String[] p = line.split(",");
            if (p.length < 4) continue;

            // match title or author
            if (p[1].toLowerCase().contains(keyword.toLowerCase()) ||
                p[2].toLowerCase().contains(keyword.toLowerCase())) {

                found = true;
                System.out.println("BookID: " + p[0] + " | Title: " + p[1] +
                                   " | Author: " + p[2] + " | Genre: " + p[3]);
                System.out.println("------------------");
            }
        }

        if (!found) System.out.println("No book found for: " + keyword);
    }

    //overriding
    public void viewTransactions(String memberID) {
        String[] trans = transactionsFH.read();
        boolean found = false;

        for (String line : trans) {
            String[] p = line.split(",");
            if (p.length < 7) continue;

            Transaction transaction = new Transaction(
                p[0], p[1], p[2],
                LocalDate.parse(p[3]),
                p[4].equals("null") ? null : LocalDate.parse(p[4]),
                Boolean.parseBoolean(p[5]),
                Double.parseDouble(p[6])
            );

            // print only this member’s transactions
            if (transaction.getMemberId().equalsIgnoreCase(memberID)) {
                found = true;
                transaction.display();
                System.out.println("------------------");
            }
        }

        if (!found) {
            System.out.println("No transactions found for member ID: " + memberID);
        }
    }

    public void viewWishlist(String memberId) { //reads wishlist file
        String[] wishlist = wishlistFH.read();
        boolean found = false;

        System.out.println("=== Wishlist for Member ID: " + memberId + " ===");

        for (String line : wishlist) {
            String[] parts = line.split(",");
            if (parts.length < 4) continue;

            if (parts[0].equalsIgnoreCase(memberId)) {
                System.out.println("Book ID: " + parts[2] + ", Book Title: " + parts[3]);
                System.out.println("------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books in wishlist for this member.");
        }
    }

    public void addToWishlist(String memberId, String bookId) {  //fetch names from files → write wishlist record

        // get member name from members.txt
        String memberName = "";
        for (String line : membersFH.read()) {
            String[] p = line.split(",");
            if (p[0].equalsIgnoreCase(memberId)) {
                memberName = p[1];
                break;
            }
        }

        // get book title from books.txt
        String bookTitle = "";
        for (String line : booksFH.read()) {
            String[] p = line.split(",");
            if (p[0].equalsIgnoreCase(bookId)) {
                bookTitle = p[1];
                break;
            }
        }

        if (bookTitle.isEmpty()) {
            System.out.println("Book ID not found. Cannot add to wishlist.");
            return;
        }

        // Format: memberId,memberName,bookId,bookTitle
        wishlistFH.append(String.join(",", memberId, memberName, bookId, bookTitle));

        System.out.println("'" + bookTitle + "' has been added to your wishlist!");
    }
}
