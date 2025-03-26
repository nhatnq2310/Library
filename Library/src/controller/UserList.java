/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import dto.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import utils.GetInput;

public class UserList extends HashMap<String, User> implements I_Manageable{

    @Override
    public void add() {
        String id, confirm;
        do {
            do {
                id = GetInput.getString("Enter ID User: ", "Wrong.Input again!");
                if (this.containsKey(id)) {
                    System.out.println("ID duplicated.Input again!");
                }
            } while (this.containsKey(id));
            String name = GetInput.getString("Enter Name: ", "Not blank.Input again!");
            Date dateOfBirh = GetInput.getDate("Enter Date Of Birth(dd-MM-yyyy): ", "Wrong format.Input again!");
            String phoneNumber = GetInput.regexString("Enter Phone Number(0xx..): ", "x is digit and 10 number.", "^[0]\\d{9}$");
            String email = GetInput.regexString("Enter Email: ", "Wrong.Input again!", "^(.+)@(\\S+)$");
            boolean active_user = true;

            this.put(id, new User(id, name, dateOfBirh, phoneNumber, email, active_user));
            System.out.println("User's information has been added.");
            confirm = GetInput.regexString("Do you want to add continues(y/n): ", "Just y or n", "^[Y|y|N|n]$");
        } while (confirm.equalsIgnoreCase("y"));
    }

    @Override
    public void update() {
        String id = GetInput.getString("Enter ID User: ", "Wrong.Input again!");
        if (!this.containsKey(id)) {
            System.out.println("User does not exist!");
        } else {
            User user = this.get(id);
            String newName = GetInput.updateString("Enter New Name: ", user.getName());
            Date newDOB = GetInput.updateDate("Enter New Date Of Birth: ", user.getDob());
            String newPhoneNumber = GetInput.updateRegexString("Enter New Phone Number(0xx..): ", "^[0]\\d{9}$", user.getPhoneNumber());
            String newEmail = GetInput.updateRegexString("Enter New Email: ", "^(.+)@(\\S+)$", user.getEmail());

            user.setName(newName);
            user.setDob(newDOB);
            user.setPhoneNumber(newPhoneNumber);
            user.setEmail(newEmail);

            System.out.println("User's information has been updated.Success.");
        }
    }

    @Override
    public void delete() {
        String id = GetInput.getString("Enter ID User: ", "Wrong.Input again!");
        if (!this.containsKey(id)) {
            System.out.println("User does not exist!");
        } else {
            User user = this.get(id);
            String confirm = GetInput.regexString("Are you sure delete(y/n): ", "Just y or n.", "^[Y|y|N|n]$");
            if (confirm.equalsIgnoreCase("y")) {
                user.setActive_user(false);
                System.out.println("User's information has been updated active(false).");
            } else {
                System.out.println("You cancel.Fail.");
            }
        }
    }

    @Override
    public void dislay() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
             TreeMap<String, User> sortedUser = new TreeMap<>(this);
            System.out.println("+-----+---------------+----------+------------+---------------+-----------+");
            System.out.println("| ID  |      NAME     |    DOB   |PHONE NUMBER|      EMAIL    |ACTIVE_BOOK|");
            System.out.println("+-----+---------------+----------+------------+---------------+-----------+");
            for (User us : sortedUser.values()) {
                System.out.printf("|%-5s|%-15s|%-10s|%-12s|%-15s|%-11s|\n", us.getId(), us.getName(),
                        GetInput.sdf.format(us.getDob()), us.getPhoneNumber(), us.getEmail(), us.isActive_user());
            
            }
            System.out.println("+-----+---------------+----------+------------+---------------+-----------+");
        }
    }
    public User getUserIsActive(String id){
        User u = null;
        for (User x : this.values()) {
            if(x.getId().equalsIgnoreCase(id) && x.isActive_user() == true){
                u = x;
            }
        }
        return u;
    }
    
    public void loadDataFromFile() {
        try {
            FileReader fr = new FileReader("Users.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String user[] = line.split(",");
                String id = user[0].trim();
                String name = user[1].trim();
                Date dateOfBirh = GetInput.sdf.parse(user[2].trim());
                String phoneNumber = user[3].trim();
                String email = user[4].trim();
                boolean active_user = Boolean.parseBoolean(user[5].trim());
                this.put(id, new User(id, name, dateOfBirh, phoneNumber, email, active_user));
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Read data from Users.txt fail!");
        }
    }

    public void saveDataToFile() {
        boolean check = true;
        try {
            FileWriter fw = new FileWriter("Users.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (User u : this.values()) {
                bw.write(u.toString());
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Save file Users.txt fail!");
            check = false;
        }
        if (check) {
            System.out.println("Save file Users.txt successful.");
        }
    }
}
