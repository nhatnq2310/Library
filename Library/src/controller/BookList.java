/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.HashMap;
import java.util.TreeMap;
import dto.Book;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import utils.GetInput;

public class BookList extends HashMap<String, Book> implements I_Manageable {
    
    @Override
    public void add() {
        String id, confirm;
        do {
            do {
                id = GetInput.getString("Enter ID Book: ", "Wrong.Input again!");
                if (this.containsKey(id)) {
                    System.out.println("ID duplicated.Input again!");
                }
            } while (this.containsKey(id));
            String title = GetInput.getString("Enter Title: ", "Not blank.Input again!");
            String author = GetInput.getString("Enter Author: ", "Not blank.Input again!");
            int publication_year = GetInput.getAnInteger("Enter Publication Year: ", "More than 0.Input again!", 1);
            String publisher = GetInput.getString("Enter Publisher: ", "Not blank.Input again!");
            String ISBN = GetInput.getString("Enter ISBN: ", "Not blank.Input again");
            boolean active_book = true;
            
            this.put(id, new Book(id, title, author, publication_year, publisher, ISBN, active_book));
            System.out.println("Book's information has been added.");
            confirm = GetInput.regexString("Do you want to add continues(y/n): ", "Just y or n.", "^[Y|y|N|n]$");
        } while (confirm.equalsIgnoreCase("y"));
    }

    @Override
    public void update() {
        String id = GetInput.getString("Enter ID Book: ", "Wrong.Input again!");
        if(!this.containsKey(id)){
            System.out.println("Book does not exist!");
        }else{
            Book bk = this.get(id);
            String newTitle = GetInput.updateString("Enter New Title: ", bk.getTitle());
            String newAuthor = GetInput.updateString("Enter New Author: ", bk.getAuthor());
            int newPublicationYear = GetInput.updateAnInteger("Enter New Publication Year: ", 1, bk.getPublication_year());
            String newPublisher = GetInput.updateString("Enter New Publisher: ", bk.getPublisher());
            String newISBN = GetInput.updateString("Enter New ISBN: ", bk.getISBN());
            
            bk.setTitle(newTitle);
            bk.setAuthor(newAuthor);
            bk.setPublication_year(newPublicationYear);
            bk.setPublisher(newPublisher);
            bk.setISBN(newISBN);
            
            System.out.println("Book's information has been updated.");
        }
    }

    @Override
    public void delete() {
        String id = GetInput.getString("Enter ID Book: ", "Wrong.Input again!");
        if(!this.containsKey(id)){
            System.out.println("Book does not exist!");
        }else{
            Book bk = this.get(id);
            String confirm = GetInput.regexString("Are you sure delete(y/n): ", "Just y or n.", "^[Y|y|N|n]$");
            if(confirm.equalsIgnoreCase("y")){
                bk.setActive_book(false);
                System.out.println("Book's information has been updated active(false).");
            }else{
                System.out.println("You cancel.Fail.");
            }
        }
    }
    
    @Override
    public void dislay(){
        if(this.isEmpty()){
            System.out.println("List empty.Nothing to print.");
        }else{
             TreeMap<String, Book> sortedBook = new TreeMap<>(this);
            System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
            System.out.println("| ID  |    TITLE      |    AUTHOR     |PUBLICATION_YEAR|    PUBLISHER  |      ISBN     |  ACTIVE_BOOK  |");
            System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
            for (Book bk : sortedBook.values()) {
               System.out.printf("|%-5s|%-15s|%-15s|%-16d|%-15s|%-15s|%-15s|\n", bk.getId(), bk.getTitle(),
                        bk.getAuthor(), bk.getPublication_year(), bk.getPublisher(), bk.getISBN(), bk.isActive_book());
            }
            System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
        }
    }
    public Book getBookIsActive(String id){
        Book b = null;
        for (Book x : this.values()) {
            if(x.getId().equalsIgnoreCase(id) && x.isActive_book() == true){
                b = x;
            }
        }
        return b;
    }
    
     public void loadDataFromFile() {
        try {
            FileReader fr = new FileReader("Books.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String book[] = line.split(",");
                String id = book[0].trim();
                String title = book[1].trim();
                String author = book[2].trim();
                int year = Integer.parseInt(book[3].trim());
                String publisher = book[4].trim();
                String ISBN = book[5].trim();
                boolean active_book = Boolean.parseBoolean(book[6].trim());
                this.put(id, new Book(id, title, author, year, publisher, ISBN, active_book));
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Read data from Books.txt fail!");
        }
    }

    public void saveDataToFile() {
        boolean check = true;
        try {
            FileWriter fw = new FileWriter("Books.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Book b : this.values()) {
                bw.write(b.toString());
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Save file Books.txt fail!");
            check = false;
        }
        if (check) {
            System.out.println("Save file Books.txt successful.");
        }
    }
    
    public void searchBookByAuthor(){
        ArrayList<Book> listBook = new ArrayList();
        String author = GetInput.getString("Enter Author: ", "Not blank.Input again!");
        
        for (Book b : this.values()) {
            if(b.getAuthor().toLowerCase().contains(author.toLowerCase())){
                listBook.add(b);
            }
        }
        if(listBook.isEmpty()){
            System.out.println("Khong tim thay sach boi ten tac gia");
        }else{
            Collections.sort(listBook, (o1, o2) -> {
                return o2.getPublication_year() - o1.getPublication_year();
            });
            System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
            System.out.println("| ID  |    TITLE      |    AUTHOR     |PUBLICATION_YEAR|    PUBLISHER  |      ISBN     |  ACTIVE_BOOK  |");
            System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
            for (Book bk : listBook) {
               System.out.printf("|%-5s|%-15s|%-15s|%-16d|%-15s|%-15s|%-15s|\n", bk.getId(), bk.getTitle(),
                        bk.getAuthor(), bk.getPublication_year(), bk.getPublisher(), bk.getISBN(), bk.isActive_book());
            }
            System.out.println("+-----+---------------+---------------+----------------+---------------+---------------+---------------+");
            
        }
                
    }
    
}
