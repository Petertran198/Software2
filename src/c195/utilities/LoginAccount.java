package c195.utilities;

import c195.dao.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class LoginAccount {

    /**
     * This method is a static method that logs into account
     * @param username
     * @param password
     * @return boolean
     */
    public static boolean logIntoAccount(String username, String password){
        String sqlStatement = "SELECT User_Name, Password FROM users WHERE " +
                "User_Name = ? AND Password = ?";
        //This is a try-with-resources statement . It makes sure the resource
        // object that we are putting inside '()' has its connection closed
        // at end of statement
        try(PreparedStatement statement =
                    JDBC.getConnection().prepareStatement(sqlStatement)){
            statement.setString(1,username);
            statement.setString(2,password);
            //A ResultSet object maintains a cursor pointing to its current row
            //of data. Initially the cursor is positioned before the first row.
            //Use .next() to moves the cursor to the next row, .next()
            // returns true or false if there is data
           ResultSet resultSet =  statement.executeQuery();
           if(resultSet.next() == true ){
                return true;
           }
        }catch(SQLException  e){
            System.out.println("Couldn't log in to account: "+ e.getMessage() );
        }
        // if no user is found
        return false;
    }
}
