package banking;

import java.util.Scanner;

public class UserInterface {
    Scanner reader = new Scanner(System.in);
    AccountsManager accountsManager;

    public void start(String DBName) {
        accountsManager = new AccountsManager(DBName);
        int choice = -1;
        int choiceInAccountMenu = -1;
        while (choice != 0) {
            printStartingMenu();
            choice = readInteger();
            switch (choice) {
                case 1:
                    accountsManager.addAccount();
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String cardNumber = reader.nextLine();
                    System.out.println("Enter your PIN:");
                    int pin = readInteger();
                    System.out.println("");
                    if (accountsManager.LogIn(cardNumber, pin)) {
                        System.out.println("You have successfully logged in!");
                        while (choiceInAccountMenu != 0 && choiceInAccountMenu != 5) {
                            printAccountMenu();
                            choiceInAccountMenu = readInteger();
                            switch (choiceInAccountMenu) {
                                case 0:
                                    choice = 0;
                                    break;
                                case 1:
                                    accountsManager.getCurrentAccBalance();
                                    break;
                                case 2:
                                    System.out.println("Enter income:");
                                    int balance = readInteger();
                                    accountsManager.addBalance(balance);
                                    break;
                                case 3:
                                    System.out.println("Transfer");
                                    System.out.println("Enter card number:");
                                    String receiver = reader.nextLine();
                                    if (accountsManager.validReceiver(receiver)) {
                                        System.out.println("Enter how much money you want to transfer:");
                                        int sumOfMoney = readInteger();
                                        accountsManager.doTransfer(sumOfMoney, receiver);
                                    }
                                    break;
                                case 4:
                                    accountsManager.closeCurrentAccount();
                                    break;
                                case 5:
                                    accountsManager.logOut();
                                    break;
                            }
                        }
                    } else
                        System.out.println("Wrong card number or PIN!");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
        accountsManager.closeDB();
    }

    public int readInteger() {
        try {
            return Integer.parseInt(reader.nextLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void printStartingMenu() {
        System.out.println("");
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
        System.out.println("");
    }

    public static void printAccountMenu() {
        System.out.println("");
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
        System.out.println("");
    }

}
