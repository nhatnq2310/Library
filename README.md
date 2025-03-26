Project Overview

The Library Management System is a Java-based application designed to manage books, members, and borrowing records efficiently. It follows a structured three-layer architecture using the DAO pattern and Factory pattern for data access and business logic.

Features

Add, update, delete, and view books.

Register and manage members.

Issue and return books with tracking.

Report generation for borrowed books and due dates.

Data storage using text files.

Project Structure

- DataChecker
  - DataChecker.java
- DataManager
  - CreateFile.java
  - LoadFromFile.java
  - SaveToFile.java
- Entity
  - Book.java
  - Member.java
- Entity.Function
  - BookList.java
  - MemberList.java
- Main
  - LibraryManager.java
- Menu
  - Menu.java

Requirements

Java 8

NetBeans 8

How to Run

Compile and run LibraryManager.java.

Ensure text files for book and member data exist.

Follow menu prompts for operations.
