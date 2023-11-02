package com.exercise.menu;

import java.util.Scanner;

import com.exercise.user.User;
import com.exercise.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMenu {
    private final UserService userService;
    private final Scanner input;

    public void menu(){
        printMenu();
        int option = input.nextInt();
        inputHandler(option);
    }

    private void inputHandler(int option) {
        if(option == 1){
            System.out.println(userService.getAllUser());
            menu();
        }
        
        if(option == 2){
            System.out.print("input id user : ");
            int id = input.nextInt();
            System.out.println(userService.getUserById(id));
            menu();
        }
        
        if(option == 3){
            System.out.print("Input username : ");
            String name = input.next();
            User user = new User(name);
            userService.insertUser(user);
            menu();
        }

        if(option == 4){
            MenuHandler menuHandler = new MenuHandler();
            menuHandler.run();
        }

        if(option <=0 || option >= 5){
            System.out.println("Input wrong");
            menu();
        }
    }

    private void printMenu(){
        System.out.println("\n+++ USER MENU +++ \n1. Get All User\n2. Get User By Id\n3. Create User\n4. Back to main menu ");
        System.out.print("input : ");
    }
}
