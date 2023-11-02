package com.exercise.menu;

import java.util.Scanner;

import com.exercise.book.Book;
import com.exercise.book.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookMenu {
    private final Scanner input;
    private final BookService bookService;

    public void menu(){
        printMenu();
        int option = input.nextInt();
        inputHandler(option);
    }

    private void inputHandler(int option) {
        switch (option) {
            case 1:
                System.out.println(bookService.getAllBooks());
                menu();
                break;
            case 2:
                System.out.print("input book by title : ");
                String title = input.next();
                System.out.println(bookService.getBooksByTitle(title));
                break;
            case 3:
                System.out.print("input book title : ");
                int id = input.nextInt();
                System.out.println(bookService.getBookById(id));
                break;
            case 4:
                Book book = setBook();
                System.out.println(bookService.insertBook(book));
                menu();
                break;
            case 5:
                System.out.print("inpur book id : ");
                id = input.nextInt();
                bookService.deleteBookById(id);
                menu();
                break;
            case 6:
                System.out.print("input id : ");
                id = input.nextInt();
                book = setBook();
                break;
            case 7:
                MenuHandler menuHandler = new MenuHandler();
                menuHandler.run();
                break;
            default:
                System.out.println("wrong inpu");
                menu();
                break;
        }
    }

    private Book setBook() {
        String title;
        System.out.print("input title : ");
        title = input.next();
        System.out.print("input author : ");
        String author = input.next();
        System.out.print("input quantity : ");
        int quantity = input.nextInt();
        Book book = new Book(title, author, quantity);
        return book;
    }

    private void printMenu(){
        System.out.println("+++ Book Menu ++ \n1. Get All Book\n2. Get Book By Title\n3. Get Book By Id\n4. Insert Book\n5. Delete Book \n6. Update Book\7. Back to main menu");
        System.out.print("Input : ");
    }
}
