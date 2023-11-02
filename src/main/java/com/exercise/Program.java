package com.exercise;

import com.exercise.menu.MenuHandler;

public class Program {
    public void run(){
        while (true) {
            try {
                MenuHandler menuHandler = new MenuHandler();
                menuHandler.run();
                break;
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

