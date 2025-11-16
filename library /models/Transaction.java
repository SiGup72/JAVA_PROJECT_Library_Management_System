package library.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {
    private String transactionId;
    private String bookId;
    private String memberId;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private boolean isReturned;
    private double fineAmount;

    // Default constructor
    public Transaction() {
        this.transactionId = "T000";
        this.bookId = "B000";
        this.memberId = "M000";
        this.issueDate = LocalDate.now();
        this.returnDate = null;
        this.isReturned = false;
        this.fineAmount = 0.0;
    }

    // Parameterized constructor
    public Transaction(String transactionId, String bookId, String memberId, LocalDate issueDate, LocalDate returnDate, boolean isReturned, double fineAmount) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.fineAmount = fineAmount;
    }

    // Getter functions
    public String getTransactionId() { return transactionId; }
    public String getBookId() { return bookId; }
    public String getMemberId() { return memberId; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return isReturned; }
    public double getFineAmount() { return fineAmount; }

    // Setter functions
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setReturned(boolean returned) { this.isReturned = returned; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    // Display method
    public void display() {
        System.out.println("TransactionID: " + transactionId + " | BookID: " + bookId + " | MemberID: " + memberId + " | IssueDate: " + issueDate + " | ReturnDate: " + (returnDate == null ? "Not Returned" : returnDate) + " | Returned: " + isReturned + " | Fine: " + fineAmount );
                
    }


}
