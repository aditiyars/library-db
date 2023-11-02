package com.exercise.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.exercise.database.Database;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookService {
        private final Database database;
        
        public List<Book> getAllBooks(){
            List<Book> books = new ArrayList<>();
            String sql = "SELECT * FROM book";
            
            try(Connection connection = this.database.connect()){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                
                while (resultSet.next()) {
                    Book book = this.convertToEntity(resultSet);
                    books.add(book);
                }

                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return books;
        }
        
        public List<Book> getBooksByTitle(String title){
            List<Book> books = new ArrayList<>();
            String sql = "SELECT * FROM book WHERE title ILIKE '%"+ title +"%'";
            
            try(Connection connection = this.database.connect()){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                
                while (resultSet.next()) {
                    Book book = this.convertToEntity(resultSet);
                    books.add(book);
                }

                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return books;
        }
        
        public Book getBookById(int id){
            Book existingBook = null;

            try(Connection connection = this.database.connect()){
                Statement statement = connection.createStatement();
                String sql = "SELECT * FROM book WHERE id = " + id;
                ResultSet resultSet = statement.executeQuery(sql);
                
                if (resultSet.next()) {
                    existingBook = convertToEntity(resultSet);
                    statement.close();
                }
                                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return existingBook;
        }

        public Book insertBook(Book book){
            String query = "INSERT INTO book (title, author, quantity) VALUES (?,?,? )";
            
            try(Connection connection = this.database.connect()) {
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());
                statement.setInt(3, book.getQuantity());

                int affectedRows = statement.executeUpdate();

                if(affectedRows > 0){
                    ResultSet resultSet = statement.getGeneratedKeys();

                    while (resultSet.next()) {
                        return this.convertToEntity(resultSet);
                    }
                }

                statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return null;

        }
        
        public Book updateBookById(int id, Book book){
            String query = "UPDATE book SET title = ?, author = ?, quantity = ? WHERE id = ?";
            
            try(Connection connection = this.database.connect()) {
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());
                statement.setInt(3, book.getQuantity());
                statement.setInt(4, id);
                int affectedRows = statement.executeUpdate();

                if(affectedRows > 0){
                    ResultSet resultSet = statement.getGeneratedKeys();

                    if (resultSet.next()) {
                        return this.convertToEntity(resultSet);
                    }
                }
                statement.close();
                throw new BookNotFoundException("Oops! Book doesnt exist");
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        public void deleteBookById(int id){
            String query = "DELETE FROM book WHERE id = ? ";
            
            try(Connection connection = this.database.connect()){
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, id);
                int affectedRows = statement.executeUpdate();
                if (affectedRows>0) {
                    System.out.println("Book with id "+ id + " deleted");
                }
                throw new BookNotFoundException("404 Book Not Found");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        private Book convertToEntity(ResultSet rs) throws SQLException {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int quantity = rs.getInt("quantity");

            return new Book(id, title, author, quantity);
        }
}
