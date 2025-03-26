/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Date;
import java.util.HashMap;
import dto.Book;
import dto.Loan;
import dto.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import utils.GetInput;

public class TransactionList extends HashMap<String, Loan> {
    
    private BookList bm;
    private UserList um;

    public TransactionList(BookList bm, UserList um) {
        this.bm = bm;
        this.um = um;
    }

    public void borrowBooks() {
        String transctionID;
        do {
            transctionID = GetInput.getString("Enter Transaction ID: ", "Wrong.Input again!");
            if (this.containsKey(transctionID)) {
                System.out.println("Duplicated ID.Input again!");
            }
        } while (this.containsKey(transctionID));
        um.dislay();
        String userID = GetInput.getString("Enter User ID: ", "Wrong.Input again!");
        User us = um.getUserIsActive(userID);
        if (us == null) {
            System.out.println("User does not exist or not active.Not borrow.");
            return;
        }

        bm.dislay();
        String bookID = GetInput.getString("Enter Book ID: ", "Wrong.Input again!");
        Book bk = bm.getBookIsActive(bookID);
        if (bk == null) {
            System.out.println("Book does not exist or not active.Not borrow.");
        } else {
            Date borrowDate = GetInput.getDate("Enter Borrow Date(dd-MM-yyyy): ", "Wrong.Input again!");
            Date returnDate = GetInput.getDate("Enter Return Date(dd-MM-yyyy): ", "Wrong.Input again!");
            this.put(transctionID, new Loan(transctionID, bookID, userID, borrowDate, returnDate));
            System.out.println("Loan's information has been added.");
        }
    }

    public void updateLoanInformation() {
        String transctionID = GetInput.getString("Enter Transaction ID: ", "Wrong.Input again!");
        if (!this.containsKey(transctionID)) {
            System.out.println("Transaction does not exist!");
        } else {
            Loan x = this.get(transctionID);
            Date newBorrowDate = GetInput.updateDate("Enter New Borrow Date(dd-MM-yyyy): ", x.getBorrowDate());
            x.setBorrowDate(newBorrowDate);
            Date returnDate = GetInput.updateDate("Enter Return Date(dd-MM-yyyy): ", x.getReturnDate());
            x.setReturnDate(returnDate);
            String confirmUser = GetInput.regexString("Do you want to change user(y/n): ", "Just y or n", "^[Y|y|N|n]$");
            if (confirmUser.equalsIgnoreCase("y")) {
                um.dislay();
                String userID = GetInput.getString("Enter ID User: ", "Wrong.Input again!");
                User us = um.getUserIsActive(userID);
                if (us == null) {
                    System.out.println("User does not exist!");
                } else {
                    x.setUserID(us.getId());
                }
            }

            String confirmBook = GetInput.regexString("Do you want to change book(y/n): ", "Just y or n", "^[Y|y|N|n]$");
            if (confirmBook.equalsIgnoreCase("y")) {
                bm.dislay();
                String bookID = GetInput.getString("Enter ID Book: ", "Wrong.Input again!");
                Book bk = bm.getBookIsActive(bookID);
                if (bk == null) {
                    System.out.println("Book does not exist!");
                } else {
                    x.setBookID(bookID);
                }
            }
        }
        System.out.println("Transaction's information has been updated.");
    }

    public void displayAllBorrowedBooks() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            System.out.println("+---------------+----------+----------+-----------+-----------+");
            System.out.println("|TRANSACTION ID | USER ID  | BOOK ID  |BORROW DATA|RETURN DATE|");
            System.out.println("+---------------+----------+----------+-----------+-----------+");
            for (Loan x : this.values()) {
                System.out.printf("|%-15s|%-10s|%-10s|%-11s|%-11s|\n", x.getTransactionID(), x.getUserID(),
                        x.getBookID(), GetInput.sdf.format(x.getBorrowDate()),GetInput.sdf.format(x.getReturnDate()));
            }
            System.out.println("+---------------+----------+----------+-----------+-----------+");
        }
    }
    
        public void reportOnBorrowedBooks() {
        HashMap<String, Book> mapBook = new HashMap();
        for (Loan x : this.values()) {
            Book book = bm.getBookIsActive(x.getBookID());
            mapBook.put(book.getId(), book);
        }
        System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
        System.out.println("| ID  |    TITLE      |    AUTHOR     |PUBLICATION_YEAR|    PUBLISHER  |      ISBN     |  ACTIVE_BOOK  |");
        System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
        for (Book bk : mapBook.values()) {
            System.out.printf("|%-5s|%-15s|%-15s|%-16d|%-15s|%-15s|%-15s|\n", bk.getId(), bk.getTitle(),
                    bk.getAuthor(), bk.getPublication_year(), bk.getPublisher(), bk.getISBN(), bk.isActive_book());
        }
        System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
    }

    public void reportOnOverdueBooks() {
        Date now = new Date();
        System.out.println("+---------------+----------+----------+-----------+-----------+");
        System.out.println("|TRANSACTION ID | USER ID  | BOOK ID  |BORROW DATA|RETURN DATE|");
        System.out.println("+---------------+----------+----------+-----------+-----------+");
        for (Loan x : this.values()) {
            if (x.getReturnDate().compareTo(now) < 0) {
                System.out.printf("|%-15s|%-10s|%-10s|%-11s|%-11s|\n", x.getTransactionID(), x.getUserID(),
                        x.getBookID(), GetInput.sdf.format(x.getBorrowDate()), GetInput.sdf.format(x.getReturnDate()));
            }
        }
        System.out.println("+---------------+----------+----------+-----------+-----------+");
    }

    public void reportOnTotalBorrowingActivities() {
        Date startDate = GetInput.getDate("Enter Start Date(dd-MM-yyyy): ", "Wrong.Input again!");
        Date endDate;
        do {
            endDate = GetInput.getDate("Enter End Date(dd-MM-yyyy): ", "Wrong.Input again!");
            if (endDate.compareTo(startDate) < 0) {
                System.out.println("End Date must be more than Start Date.");
            }
        } while (endDate.compareTo(startDate) < 0);
        System.out.println("+---------------+----------+----------+-----------+-----------+");
        System.out.println("|TRANSACTION ID | USER ID  | BOOK ID  |BORROW DATA|RETURN DATE|");
        System.out.println("+---------------+----------+----------+-----------+-----------+");
        for (Loan x : this.values()) {
            if (x.getBorrowDate().compareTo(startDate) >= 0 && x.getBorrowDate().compareTo(endDate) <= 0) {
                System.out.printf("|%-15s|%-10s|%-10s|%-11s|%-11s|\n", x.getTransactionID(), x.getUserID(),
                        x.getBookID(), GetInput.sdf.format(x.getBorrowDate()),GetInput.sdf.format(x.getReturnDate()));
            }
        }

        System.out.println("+---------------+----------+----------+-----------+-----------+");
    }


    public void loadDataFromFile() {
        try {
            FileReader fr = new FileReader("Loans.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String transaction[] = line.split(",");
                String transactionID = transaction[0].trim();
                String bookID = transaction[1].trim();
                String userID = transaction[2].trim();
                Date borrowDate = GetInput.sdf.parse(transaction[3].trim());
                Date returnDate = GetInput.sdf.parse(transaction[4].trim());
                this.put(transactionID, new Loan(transactionID, bookID, userID, borrowDate, returnDate));
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Read data from Loans.txt fail!");
        }
    }

    public void saveDataToFile() {
        boolean check = true;
        try {
            FileWriter fw = new FileWriter("Loans.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Loan t : this.values()) {
                bw.write(t.toString());
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Save file Loans.txt fail!");
            check = false;
        }
        if (check) {
            System.out.println("Save file Loans.txt successful.");
        }
    }

}
