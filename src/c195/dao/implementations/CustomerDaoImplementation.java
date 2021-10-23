package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Country;
import c195.model.Customer;
import c195.model.FLDivision;
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

    @Override
    public void addCustomer(Customer customer) {
        String sql = "Insert into Customer(Customer_Name, Address, " +
                "Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement =
                    JDBC.getConnection().prepareStatement(sql)){
            preparedStatement.setString(1,customer.getCustomer_Name());
            preparedStatement.setString(2,customer.getAddress());
            preparedStatement.setString(3,customer.getPostal_code());
            preparedStatement.setString(4,customer.getPhone());
            preparedStatement.setInt(5,customer.getDivision_ID());
        }catch(SQLException e){
            System.out.println("Problem in addCustomer method" + e.getMessage());
        }
    }

    @Override
    public ObservableList<Country> getCountryList() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String sql = "SELECT Country,Country_ID FROM countries";
        try{
            Statement statement =
                    JDBC.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Country country = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                countries.add(country);
            }
        }catch(SQLException e){
            System.out.println("Error in getCountry method " + e.getMessage());
        }
        return countries;

    }


    @Override
    public ObservableList<FLDivision> getFLDDivisionList() {
        ObservableList<FLDivision> divisions =
                FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM " +
                "first_level_divisions";
        try(
                Statement statement = JDBC.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                FLDivision division = new FLDivision(
                        resultSet.getInt(
                        "Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID")
                );
                divisions.add(division);
            }
        }catch(SQLException e){
            System.out.println("Error in getFLDDivisionList method"+ e.getMessage());
        }
        return divisions;
    }
}
