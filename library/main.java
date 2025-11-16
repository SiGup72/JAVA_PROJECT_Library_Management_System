package library;

import library.models.*;
import library.filehandling.*;
import library.services.*;
import java.util.*;
import java.time.LocalDate;

class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("==============================================");
        System.out.println("  ~ WELCOME TO LIBRARY MANAGEMENT SYSTEM ~  ");
        System.out.println("==============================================");
        System.out.println("Please enter your credentials");

        System.out.print("Enter username (Member or Librarian): ");
        String username = scan.nextLine();
        System.out.print("Enter password: ");
        String password = scan.nextLine();

        boolean isLibrarian = username.equalsIgnoreCase("Lib1234") && password.equals("0000"); // permanent credentials
        boolean isMember = false;
        if (!isLibrarian) {  // checks member id
            MembersFH membersFH = new MembersFH();
            String[] members = membersFH.read(); // read all members from file
            for (String line : members) {
                String[] p = line.split(","); // Format: memberID,name,email,joindate
                if (p[0].equalsIgnoreCase(username) && password.equals("1234")) {
                    isMember = true;
                    break;
                }
            }
        }

        if (!isLibrarian && !isMember) {
            System.out.println("Invalid credentials! Exiting...");
            return;
        }

        if (isLibrarian) {
            LibrarianService librarian = new LibrarianService();
            int choice = 0;
            do {
                System.out.println("\n===== LIBRARIAN MENU =====");
                System.out.println("1. Add Book");
                System.out.println("2. Add Member");
                System.out.println("3. Issue Book");
                System.out.println("4. Return Book");
                System.out.println("5. View Transactions");
                System.out.println("6. Search Book");
                System.out.println("7. View Members");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
                switch (choice) {
                    case 1: { // Add Book
                        System.out.print("Enter Book ID: ");
                        String bookID = scan.nextLine();
                        System.out.print("Enter Book Title: ");
                        String title = scan.nextLine();
                        System.out.print("Enter Book Author: ");
                        String author = scan.nextLine();
                        System.out.print("Enter Book Genre: ");
                        String genre = scan.nextLine();
                        Book book = new Book(bookID, title, author, genre); 
                        librarian.addBook(book);
                        librarian.viewAllBooks();
                        break;
                    }
                    case 2: { // Add Member
                        System.out.print("Enter Member ID: ");
                        String memberID = scan.nextLine();
                        System.out.print("Enter Member Name: ");
                        String name = scan.nextLine();
                        System.out.print("Enter Member Email: ");
                        String email = scan.nextLine();
                        Member member = new Member(memberID, name, email, LocalDate.now()); 
                        librarian.addMember(member);
                        librarian.viewAllMembers();
                        break;
                    }
                    case 3: { // Issue Book
                        librarian.viewAllBooks();
                        System.out.print("Enter Member ID: ");
                        String memberID = scan.nextLine();
                        System.out.print("Enter Book ID to issue: ");
                        String bookID = scan.nextLine();
                        librarian.issueBook(memberID, bookID);
                        librarian.viewIssuedBooks(memberID);
                        break;
                    }
                    case 4: { // Return Book
                        System.out.print("Enter Member ID: ");
                        String memberID = scan.nextLine();
                        if(librarian.viewIssuedBooks(memberID)) {
                        System.out.print("Enter Book ID to return: ");
                        String bookID = scan.nextLine();
                        librarian.returnBook(memberID, bookID); }
                        else {
                        System.out.println("No books are currently issued to you.");
                        }
                        break;
                    }
                    case 5: { // View Transactions
                        System.out.println("1. View all transactions");
                        System.out.println("2. View transactions of a specific member");
                        System.out.print("Enter your choice: ");
                        int transactionChoice = Integer.parseInt(scan.nextLine());
                        if (transactionChoice == 1) {
                            librarian.viewTransactions(null); // show all transactions
                        } else if (transactionChoice == 2) {
                            System.out.print("Enter Member ID: ");
                            String memberID = scan.nextLine();
                            librarian.viewTransactions(memberID); // show only one member's transactions
                        } else {
                            System.out.println("Invalid choice!");
                        }
                        break;
                    }
                    case 6: { // Search Book
                        System.out.print("Enter book title or author to search: ");
                        String key = scan.nextLine();
                        librarian.searchBook(key);
                        break;
                    }
                    case 7: {
                        librarian.viewMembers();
                        break;
                    }
                    case 8: {
                        System.out.println("Exiting...");
                        break;
                    }
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } 
            while (choice != 8);
        }

        else if (isMember) {
            MemberService member = new MemberService();
            int choice = 0;
            do {
                System.out.println("\n===== MEMBER MENU =====");
                System.out.println("1. Issue Book");
                System.out.println("2. Return Book");
                System.out.println("3. Search Book");
                System.out.println("4. View My Transactions");
                System.out.println("5. View Wishlist");
                System.out.println("6. Add to Wishlist");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
                switch (choice) {
                    case 1: {
                        member.viewAllBooks();
                        System.out.print("Enter Book ID to issue: ");
                        String bookID = scan.nextLine();
                        member.issueBook(username, bookID);
                        member.viewIssuedBooks(username);
                        break;
                    }
                    case 2: {
                        if(member.viewIssuedBooks(username)) {
                        System.out.print("Enter Book ID to return: ");
                        String bookID = scan.nextLine();
                        member.returnBook(username, bookID); }
                        else {
                        System.out.println("No books are currently issued to you.");
                        }
                        break;
                    }
                    case 3: {
                        System.out.print("Enter book title or author to search: ");
                        String key = scan.nextLine();
                        member.searchBook(key);
                        break;
                    }
                    case 4: {
                        member.viewTransactions(username);
                        break;
                    }
                    case 5: {
                        member.viewWishlist(username);
                        break;
                    }
                    case 6: { // Add to Wishlist
                        member.viewAllBooks();
                        System.out.print("Enter Book ID to add to wishlist: ");
                        String bookID = scan.nextLine();
                        member.addToWishlist(username, bookID); // memberName and bookTitle handled inside method
                        break;
                    }
                    case 7: {
                        System.out.println("Exiting...");
                        break;
                    }
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } 
            while (choice != 7);
        }

        System.out.println("==================");
        System.out.println("  ~ THANK YOU ~  ");
        System.out.println("==================");
    }
}
