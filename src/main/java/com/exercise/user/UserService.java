package com.exercise.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.exercise.database.Database;

public class UserService {
    Database database = new Database();
    
    public List<User> getAllUser(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user_tb";
        try(Connection connection = this.database.connect()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                User user = convertToEntity(rs);
                users.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
    
    public User getUserById(int id){
        
        String sql = String.format("SELECT * FROM user_tb WHERE id = %o ", id);
        User existingUser = null;

        try(Connection connection = this.database.connect()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            if(rs.next()) {
                existingUser = convertToEntity(rs);
                statement.close();
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if(existingUser.equals(null)){
            throw new UserNotFoundException("Oops! User does not found");
        }
        
        return existingUser;
    }

    public User insertUser(User user){
        String query = "INSERT INTO user_tb (name) VALUES (?)";

        try(Connection connection = this.database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            
            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                
                if (resultSet.next()) {
                    System.out.println("Create user successfully");
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

    private User convertToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new User(id, name);
    }
    
}
