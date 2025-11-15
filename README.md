# Library Management Project

A Java-based Library Management System that allows managing books, members, and transactions in a library. 

**Features:**
- Two types of users: Librarian (full access) and Member (limited access).
- Add and manage books with details like title, author, and genre.
- Add and manage members with basic information and join date.
- Issue and return books, tracking transactions automatically.
- Search books by title or author.
- View all transactions or transactions of a specific member.
- View all members or details of a specific member.
- Member wishlist to keep track of books they want.

This project demonstrates object-oriented programming concepts in Java, including classes, inheritance, and file handling, while creating a functional library system with role-based access


## File Structure 
'''
Library_Management_Project
│
├── library
│   ├── main.java
│   │
│   ├── models
│   │   ├── Person.java
│   │   ├── Member.java
│   │   ├── Book.java
│   │   ├── Librarian.java
│   │   └── Transaction.java
│   │
│   ├── filehandling
│   │   ├── FileHandler.java
│   │   ├── MembersFH.java
│   │   ├── BooksFH.java
│   │   ├── TransactionsFH.java
│   │   └── WishlistFH.java
│   │
│   └── services
│       ├── LibraryService.java
│       ├── LibrarianService.java
│       └── MemberService.java
│
├── books.txt
├── members.txt
├── transactions.txt
└── wishlist.txt
'''

## How to Run
1. Compile: `javac -d . library/models/*.java library/filehandling/*.java library/services/*.java library/main.java`
2. Run: `java library.main`
