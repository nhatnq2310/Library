package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
import java.util.ArrayList;
import utils.GetInput;

public class Menu extends ArrayList<String>{

    public Menu() {
        super();
    }

    public void addItem(String str) {
        this.add(str);
    }

    public int getChoice() {
        return GetInput.getAnInteger("Enter Choice[1.." + this.size() + "]: ", "Just an integer and in range 1 to " + this.size(), 1, this.size());
    }

    public void showMenu() {
        for (int i = 0; i < this.size(); i++) {
            System.out.println(this.get(i));
        }
    }
  

}
