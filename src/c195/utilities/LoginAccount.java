package c195.utilities;

import c195.dao.JDBC;
import c195.model.User;

import java.sql.*;

/**
 * Utility for logging into accounts
 */
public class LoginAccount {

    /**
     * This method is a static method that logs into account
     * @param username
     * @param password
     * @return true if there is a user with that username and password
     */
    public static User logIntoAccount(String username, String password) throws SQLException {
        String sqlStatement = "SELECT User_Name, Password, User_ID FROM users " +
                "WHERE " +
                "User_Name = ? AND Password = ?";
        User user = null;

        try( PreparedStatement statement =
                                JDBC.getConnection().prepareStatement(sqlStatement);){
            statement.setString(1, username);
            statement.setString(2, password);
            //A ResultSet object maintains a cursor pointing to its current row
            //of data. Initially the cursor is positioned before the first row.
            //Use .next() to moves the cursor to the next row, .next()
            // returns true or false if there is data
             ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int user_id = resultSet.getInt("User_ID");
                String user_name = resultSet.getString("User_Name");
                user = new User(user_id,user_name);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't log in to account: " + e.getMessage());
        }
        // if no user is found
        return user;
    }
}
