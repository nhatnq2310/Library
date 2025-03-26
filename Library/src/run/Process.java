/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import controller.BookList;
import controller.TransactionList;
import controller.UserList;
import view.Menu;

public class Process {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.addItem("             1. Manage Books.");
        menu.addItem("             2. Manage Users.");
        menu.addItem("             3. Managing Borrowing and Returning Books.");
        menu.addItem("             4. Reporting.");
        menu.addItem("             5. Store Data to Files.");
        menu.addItem("             6. Exist!");

        BookList bm = new BookList();
        bm.loadDataFromFile();
        UserList um = new UserList();
        um.loadDataFromFile();
        TransactionList tm = new TransactionList(bm, um);
        tm.loadDataFromFile();

        int choice;
        do {
            System.out.println("Welcome to Library Management.");
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    Menu menuBook = new Menu();
                    menuBook.addItem("             1. Add a New Book.");
                    menuBook.addItem("             2. Update Book Information.");
                    menuBook.addItem("             3. Delete a Book.");
                    menuBook.addItem("             4. Show All Books.");
                    menuBook.addItem("             5. Search Books BY author.");
                    menuBook.addItem("             6. Return main menu!");
                    int choiceBook;
                    do {
                        System.out.println("Welcome to Book Management.");
                        menuBook.showMenu();
                        choiceBook = menuBook.getChoice();
                        switch (choiceBook) {
                            case 1:
                                bm.add();
                                break;
                            case 2:
                                bm.update();
                                break;
                            case 3:
                                bm.delete();
                                break;
                            case 4:
                                bm.dislay();
                                break;
                            case 5:
                                bm.searchBookByAuthor();
                                break;
                            case 6:
                                System.out.println("Quit");
                                break;
                        }
                    } while (choiceBook != 6);
                    break;
                case 2:
                    Menu menuUser = new Menu();
                    menuUser.addItem("             1. Add a New User.");
                    menuUser.addItem("             2. Update User Information.");
                    menuUser.addItem("             3. Delete a User.");
                    menuUser.addItem("             4. Show All Users.");
                    menuUser.addItem("             5. Return main menu!");
                    int choiceUser;
                    do {
                        System.out.println("Welcome to User Management.");
                        menuUser.showMenu();
                        choiceUser = menuUser.getChoice();
                        switch (choiceUser) {
                            case 1:
                                um.add();
                                break;
                            case 2:
                                um.update();
                                break;
                            case 3:
                                um.delete();
                                break;
                            case 4:
                                um.dislay();
                                break;
                            case 5:
                                System.out.println("Quit");
                                break;
                        }
                    } while (choiceUser != 5);
                    break;
                case 3:
                    Menu menuLoan = new Menu();
                    menuLoan.addItem("             1. Borrow Books.");
                    menuLoan.addItem("             2. Update Loan Information.");
                    menuLoan.addItem("             3. Display All Borrowed Books.");
                    menuLoan.addItem("             4. Return main menu!");
                    int choiceLoan;
                    do {
                        System.out.println("Welcome to Transaction Management.");
                        menuLoan.showMenu();
                        choiceLoan = menuLoan.getChoice();
                        switch (choiceLoan) {
                            case 1:
                                tm.borrowBooks();
                                break;
                            case 2:
                                tm.updateLoanInformation();
                                break;
                            case 3:
                                tm.displayAllBorrowedBooks();
                                break;
                            case 4:
                                System.out.println("Quit!");
                                break;
                        }
                    } while (choiceLoan != 4);
                    break;
                case 4:
                    Menu menuReport = new Menu();
                    menuReport.addItem("             1. Report on Borrowed Books.");
                    menuReport.addItem("             2. Report on Overdue Books.");
                    menuReport.addItem("             3. Report on Total Borrowing Activities.");
                    menuReport.addItem("             4. Return main menu!");
                    int choiceReport;
                    do {
                        System.out.println("Welcome to Report Management.");
                        menuReport.showMenu();
                        choiceReport = menuReport.getChoice();
                        switch (choiceReport) {
                            case 1:
                                tm.reportOnBorrowedBooks();
                                break;
                            case 2:
                                tm.reportOnOverdueBooks();
                                break;
                            case 3:
                                tm.reportOnTotalBorrowingActivities();
                                break;
                            case 4:
                                System.out.println("Quit!");
                                break;
                        }
                    } while (choiceReport != 4);
                    break;
                case 5:
                    bm.saveDataToFile();
                    um.saveDataToFile();
                    tm.saveDataToFile();
                    break;
                case 6:
                    System.out.println("Bye bye. See you next time!");
                    break;
            }
        } while (choice != 6);
    }
}
