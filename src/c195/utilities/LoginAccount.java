package c195.utilities;

import c195.dao.JDBC;

import java.sql.*;

public class LoginAccount {

    /**
     * This method is a static method that logs into account
     *
     * @param username
     * @param password
     * @return boolean
     */
    public static boolean logIntoAccount(String username, String password) throws SQLException {
        String sqlStatement = "SELECT User_Name, Password FROM users WHERE " +
                "User_Name = ? AND Password = ?";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //Useful because we have to close the resultSet, statement, and
        //connection ONLY after the user is found aka last thing to do in
        // this method therefore if I returned too EARLY before these
        // resources are actually found it will give a
        // 'No operations allowed after connection closed'.
        // When logging in user.
        boolean afterAllResourceArePopulatedAndClose = false;
        //This is a try-with-resources statement . It makes sure the resource
        // object that we are putting inside '()' has its connection closed
        // at end of statement
        //Therefore we do not have to close con or statement as it is in ()
        //but we will have to close the resultSet
        try {
            statement = JDBC.getConnection().prepareStatement(sqlStatement);
            statement.setString(1, username);
            statement.setString(2, password);
            //A ResultSet object maintains a cursor pointing to its current row
            //of data. Initially the cursor is positioned before the first row.
            //Use .next() to moves the cursor to the next row, .next()
            // returns true or false if there is data
             resultSet = statement.executeQuery();
            if(resultSet.next()){
                afterAllResourceArePopulatedAndClose = true;
            }
        } catch (SQLException e) {
            System.out.println("Couldn't log in to account: " + e.getMessage());
        }finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {};
            try { if (statement != null) statement.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
            if(afterAllResourceArePopulatedAndClose == true){
                return true;
            }
        }
        // if no user is found
        return false;
    }
}
