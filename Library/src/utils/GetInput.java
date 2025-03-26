/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GetInput {

    public static Scanner sc = new Scanner(System.in);
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static int getAnInteger(String inputMsg, String errorMsg, int min) {
        int n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < min) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static int getAnInteger(String inputMsg, String errorMsg, int min, int max) {
        int n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < min || n > max) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static String getString(String inputMsg, String errorMsg) {
        String id;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine();
            if (id.length() == 0 || id.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

    public static String regexString(String inputMsg, String errorMsg, String format) {
        String id;
        boolean match;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine();
            match = id.matches(format);
            if (id.length() == 0 || id.isEmpty() || match == false) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

    public static Date getDate(String inputMsg, String errorMsg) {
        String data;
        while (true) {
            System.out.print(inputMsg);
            data = sc.nextLine().trim();
            try {
                sdf.setLenient(false);
                sdf.parse(data);
                return sdf.parse(data);
            } catch (ParseException e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static Date updateDate(String inputMsg, Date oldData) {
        Date result = oldData;
        System.out.printf(inputMsg);
        String tmp = sc.nextLine();
        if (!tmp.isEmpty()) {
            boolean check = true;
            try {
                sdf.setLenient(false);
                result = sdf.parse(tmp);
            } catch (ParseException e) {
                System.out.println("Wrong format.Input again!");
                check = false;
            }
            if (check == false) {
                result = getDate("Enter Date(Format as dd-MM-yyyy): ", "Wrong.Input again!");
            }
        }
        return result;
    }

    public static String updateString(String inputMsg, String oldData) {
        String result = oldData;
        System.out.printf(inputMsg);
        String tmp = sc.nextLine();
        if (!tmp.isEmpty()) {
            result = tmp;
        }
        return result;
    }

    public static String updateRegexString(String inputMsg, String format, String oldData) {
        String result = oldData;
        System.out.printf(inputMsg);
        String tmp = sc.nextLine();
        if (!tmp.isEmpty()) {
            boolean check = tmp.matches(format);
            if (check == false) {
                tmp = regexString(inputMsg, "Wrong.Input again!", format);
            }
            result = tmp;
        }
        return result;
    }

    public static int updateAnInteger(String inputMsg, int min, int oldData) {
        boolean check = true;
        int number = oldData;
        do {
            try {
                System.out.print(inputMsg);
                String tmp = sc.nextLine();
                if (tmp.isEmpty()) {
                    check = false;
                } else {
                    number = Integer.parseInt(tmp);
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check == true || number < min);
        return number;
    }

}
