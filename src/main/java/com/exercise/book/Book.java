package com.exercise.book;

import com.exercise.library.OutOfStockException;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
    private int quantity;
    
    public Book(String title, String auhtor, int quantity) {
        this.title = title;
        this.author = auhtor;
        this.quantity = quantity;
    }

    public void decreaseQuantity(){
        if(this.quantity <= 0){
            throw new OutOfStockException("Oops! quantity <= 0 ");
        }

        this.quantity = this.quantity -1;
    }

    public void increaseQuantity(){
        this.quantity = this.quantity + 1;
    }

    @Override
    public String toString() {
        return "\nid = " + id + "\ntitle = " + title + "\nauthor = " + author + "\nquantity = " + quantity + "\n";
    }


}
