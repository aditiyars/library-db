package com.exercise.menu;

import java.util.Scanner;
import com.exercise.library.BorrowingRecordService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminMenu {
    private final Scanner input;
    private final BorrowingRecordService borrowingRecordService;

    public void menu(){
        printMenu();
        int option = input.nextInt();
        inputHandler(option);
    }

    private void inputHandler(int option) {
        switch (option) {
            case 1:
                System.out.println(borrowingRecordService.getAllRecord());
                menu();
                break;
            case 2:
                System.out.print("input borrowing record id : ");
                int id = input.nextInt();
                System.out.println(borrowingRecordService.getRecordById(id));
                menu();
                break;
            case 3:
                System.out.print("input user id : ");
                int userId = input.nextInt();
                System.out.print("input book id : ");
                int bookId = input.nextInt();
                System.out.println(borrowingRecordService.createBorrowingRecord(userId, bookId));
                menu();
                break;
            case 4:
                System.out.print("Input borrowing record id : ");
                int recordId = input.nextInt();
                System.out.print("Input borrowing user id : ");
                userId = input.nextInt();
                borrowingRecordService.returnBook(recordId, userId);
                break;
            case 5:
                MenuHandler menuHandler = new MenuHandler();
                menuHandler.run();
                break;
            default:
                System.out.println("wrong input");
                menu();
                break;
        }
    }

    private void printMenu(){
        System.out.println("+++ ADMIN MENU +++\n1. Get all borrowing record\n2. Get borrowing record by id\n3. Borrow book\n4. Return book\n5. Back to main menu");
        System.out.print("input : ");
    }
}
