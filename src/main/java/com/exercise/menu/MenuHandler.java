package com.exercise.menu;

import java.util.Scanner;

import com.exercise.book.BookService;
import com.exercise.database.Database;
import com.exercise.library.BorrowingRecordService;
import com.exercise.user.UserService;

public class MenuHandler {
    private Scanner input = new Scanner(System.in);
    private UserService userService = new UserService();
    private Database database = new Database();
    private BookService bookService = new BookService(database);
    private BorrowingRecordService borrowingRecordService = new BorrowingRecordService(this.database,this.bookService, userService);

    public void printMenu(){
        System.out.println("\n+++ MAIN MENU +++\n1. Admin\n2. User\n3. Book (optional)\n4. Exit");
        System.out.print("input : ");
    }

    public void run(){
        printMenu();
        int option = input.nextInt();
        inputHandler(option);
    }

    private void inputHandler(int option) {
        switch (option) {
            case 1:
                AdminMenu adminMenu = new AdminMenu(input, borrowingRecordService);
                adminMenu.menu();
                break;
            case 2:
                UserMenu userMenu = new UserMenu(userService, input);
                userMenu.menu();
                break;
            case 4:
                        
                break;
            case 3:
                System.out.println("=== Thankyou === ");        
                break;
            default:
                System.out.println("Wrong input");
                run();
                break;
        }
        
    }

}
