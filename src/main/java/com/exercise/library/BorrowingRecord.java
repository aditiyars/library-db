package com.exercise.library;

import java.sql.Timestamp;

import com.exercise.book.BookAlreadyReturnedException;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BorrowingRecord {
    private int id;
    private int book_id;
    private int user_id;
    private Timestamp borrowed_at;
    private Timestamp returned_at;
    
    public void setReturned_at(Timestamp returned_at) {
        if (returned_at != null) {
            throw new BookAlreadyReturnedException("Oops! you already return the book");
        }
        
        this.returned_at = returned_at;
    }
    
    @Override
    public String toString() {
        return String.format("\nid = %s \nbook id = %s\nuser id = %s \nborrowed at = %s\nreturned at = %s\n", id, book_id, user_id, borrowed_at, returned_at);
    }
    
}
