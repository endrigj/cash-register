package com.demo;

import java.util.Scanner;


/**
 * The main class that receives and processes commands.
 */
public class CashRegisterApplication {

    /**
     * Main method the receives and processes commands.
     *
     * @param args possible commands: put, take, show, change, end
     */
    public static void main(String[] args) {
        System.out.println("Starting the application, ready");

        CashRegisterController controller = new CashRegisterController();
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        boolean running = true;
        while (running) {
            String operation = command.split(" ")[0];
            switch (operation) {
                case "put":
                    System.out.println(controller.putAndShow(command));
                    break;
                case "take":
                    System.out.println(controller.takeAndShow(command));
                    break;
                case "show":
                    System.out.println(controller.show());
                    break;
                case "change":
                    System.out.println(controller.change(command));
                    break;
                case "end":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command");
            }
            command = scanner.nextLine();
        }
        scanner.close();
    }
}
