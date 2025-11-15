# Library Management Project

A Java-based Library Management System that allows managing books, members, and transactions in a library.
- Book management
- Member management
- Book issue/return system
- Transactions logging
- Member wishlist feature


## File Structure 

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


## How to Run

1. Compile: `javac -d . library/models/*.java library/filehandling/*.java library/services/*.java library/main.java`
2. Run: `java library.main`
