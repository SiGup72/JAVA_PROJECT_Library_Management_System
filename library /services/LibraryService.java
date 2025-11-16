package library.services;

import java.util.*;

public interface LibraryService {
    void issueBook(String memberId, String bookId);
    void returnBook(String memberId, String bookId);
    void searchBook(String keyword);
    void viewTransactions(String memberId);
}
