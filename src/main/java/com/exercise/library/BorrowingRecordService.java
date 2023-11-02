package com.exercise.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.exercise.book.Book;
import com.exercise.book.BookService;
import com.exercise.database.Database;
import com.exercise.user.User;
import com.exercise.user.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BorrowingRecordService {
    private final Database database;
    private final BookService bookService;
    private final UserService userService;

    public List<BorrowingRecord> getAllRecord(){
        List<BorrowingRecord> borrowingRecords = new ArrayList<>();
        String sql = "SELECT * FROM borrowing_record";

        try(Connection connection = this.database.connect()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                BorrowingRecord borrowingRecord = convertToEntity(resultSet);
                borrowingRecords.add(borrowingRecord);
            }

            statement.close();
            return borrowingRecords;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return borrowingRecords;
    }

    
    
    public BorrowingRecord getRecordById(int id){
        String sql = String.format("SELECT * FROM borrowing_record WHERE id = %s ", id);
        try(Connection connection = this.database.connect()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return convertToEntity(resultSet);
            }
            else{
                throw new BorrowingRecordNotFoundException("Oops! Borrowing record not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public BorrowingRecord createBorrowingRecord(int userID, int bookID){
        String query = "INSERT INTO borrowing_record (book_id, user_id, borrowed_at, returned_at) VALUES (?, ?, ?, ?)";

        try(Connection connection = this.database.connect()) {
            Book book = this.bookService.getBookById(bookID);
            book.decreaseQuantity();
            User user = this.userService.getUserById(userID);
            
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, book.getId());
            statement.setInt(2, user.getId());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(4, null);
            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    Book updatedBook = new Book(book.getTitle(), book.getAuthor(), book.getQuantity()-1);
                    bookService.updateBookById(bookID, updatedBook);
                    System.out.println("Successfulyy borrowed a book");
                    return convertToEntity(resultSet);
                }
            }
            
            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void returnBook(int borrowingRecordId, int userId){
        BorrowingRecord borrowingRecord = this.getRecordById(borrowingRecordId);
        Book book = this.bookService.getBookById(borrowingRecord.getBook_id());
        book.increaseQuantity();
        Timestamp returnedBookAt = Timestamp.valueOf(LocalDateTime.now());
        borrowingRecord.setReturned_at(returnedBookAt);
        updateBorrowingRecordsByid(borrowingRecordId, borrowingRecord);
    }

    private BorrowingRecord updateBorrowingRecordsByid(int borrowingRecordId, BorrowingRecord borrowingRecord) {
        String query = "UPDATE borrowing_record SET book_id = ?, user_id = ?, borrowed_at = ?, returned_at = ? WHERE id = ?";
        try(Connection connection = this.database.connect()) {
                
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, borrowingRecord.getBook_id());
            statement.setInt(2, borrowingRecord.getUser_id());
            statement.setTimestamp(3, borrowingRecord.getBorrowed_at());
            statement.setTimestamp(4, borrowingRecord.getReturned_at());
            statement.setInt(5, borrowingRecordId);
            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    statement.close();
                    return convertToEntity(resultSet);                
                }

            }else{
                throw new BorrowingRecordNotFoundException("Oops Borrowing Record not found");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private BorrowingRecord convertToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int book_id = resultSet.getInt("book_id");
        int user_id = resultSet.getInt("user_id");
        Timestamp borrowed_at  = resultSet.getTimestamp("borrowed_at");
        Timestamp returned_at  = resultSet.getTimestamp("returned_at");
        return new BorrowingRecord(id, book_id, user_id, borrowed_at, returned_at);
    }
}
