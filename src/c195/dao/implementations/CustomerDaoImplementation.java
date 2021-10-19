package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

        try(Connection con = JDBC.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {
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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }
}
