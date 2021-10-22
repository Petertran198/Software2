package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerDaoImplementation implements CustomerDaoInterface {

    @Override
    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> customers =
                FXCollections.observableArrayList();
        String sql = "SELECT " +
                    "cus.Customer_ID, cus.Customer_Name, cus.Address, d" +
                    ".Division, cou.Country, cus.Phone " +
                    "FROM customers cus " +
                        "INNER JOIN first_level_divisions d " +
                            "ON cus.Division_ID = d.Division_ID " +
                        "INNER JOIN countries cou " +
                            " ON d.Country_ID = cou.Country_ID";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
             connection = JDBC.getConnection();
             statement = connection.createStatement();
             resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                int Customer_ID = resultSet.getInt("Customer_ID");
                String Customer_name = resultSet.getString("Customer_Name");
                String Address = resultSet.getString("Address");
                String Division = resultSet.getString("Division");
                String Country = resultSet.getString("Country");
                String Phone = resultSet.getString("Phone");

                Customer customer = new Customer(Customer_ID,Customer_name,
                        Address,Division,Country,Phone);
                customers.add(customer);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void deleteCustomer(int id) {
        String deleteCustomerAppointmentSql = "Delete From appointments WHERE" +
                " Customer_ID = ?";
        String deleteCustomerSql = "Delete FROM customers WHERE Customer_ID =" +
                " ?";
        Connection con = null;
        PreparedStatement deleteAppointmentStatement = null;
        PreparedStatement deleteCustomerStatement = null;

        try{
            con = JDBC.getConnection();
            deleteAppointmentStatement =
                    con.prepareStatement(deleteCustomerAppointmentSql);
            deleteAppointmentStatement.setInt(1, id);
            deleteCustomerStatement = con.prepareStatement(deleteCustomerSql);
            deleteCustomerStatement.setInt(1, id);
            //executeUpdate does not return a resultSet
            deleteAppointmentStatement.executeUpdate();
            deleteCustomerStatement.executeUpdate();
            deleteAppointmentStatement.close();
            deleteCustomerStatement.close();
        }catch(SQLException e ){
            System.out.println("Error in deleteCustomer() "+ e.getMessage());
        }
    }
}
