package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Country;
import c195.model.Customer;
import c195.model.FLDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Contains DB methods for handling Customers
 */
public class CustomerDaoImplementation implements CustomerDaoInterface {

    /**
     *
     * @return Observablelist of all Customers
     */
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

    /**
     * Delete a specific customer from db with the specified id
     * @param id take a customer_id
     */
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

    /**
     * Save a customer to db
     * @param customer Customer instance to add to db
     */
    @Override
    public void addCustomer(Customer customer) {
        String sql = "Insert into Customers(Customer_Name, Address, " +
                "Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement =
                    JDBC.getConnection().prepareStatement(sql)){
            preparedStatement.setString(1,customer.getCustomer_Name());
            preparedStatement.setString(2,customer.getAddress());
            preparedStatement.setString(3,customer.getPostal_code());
            preparedStatement.setString(4,customer.getPhone());
            preparedStatement.setInt(5,customer.getDivision_ID());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Problem in addCustomer method" + e.getMessage());
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "Update customers SET Customer_Name =?, Address=?, " +
                "Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID =?";
        try(PreparedStatement preparedStatement =
                    JDBC.getConnection().prepareStatement(sql)){
            preparedStatement.setString(1,customer.getCustomer_Name());
            preparedStatement.setString(2,customer.getAddress());
            preparedStatement.setString(3,customer.getPostal_code());
            preparedStatement.setString(4,customer.getPhone());
            preparedStatement.setInt(5,customer.getDivision_ID());
            preparedStatement.setInt(6,customer.getCustomer_ID());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Problem in addCustomer method" + e.getMessage());
        }
    }

    /**
     *
     * @return ObservableList of ALL Country, each country includes country names and
     * ids
     */
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
                "first_level_divisions ";
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
            System.out.println("Error in getFLDDivisionList()"+ e.getMessage());
        }
        return divisions;
    }

    /**
     * returns a customer
     * @param id customer_id
     * @return a customer with that specific id
     */
    @Override
    public Customer findCustomer(int id) {
        String sql = "SELECT cus.Customer_ID, cus.Customer_Name, cus.Address," +
                "cus.Phone, cus.Postal_Code, d.Division, cou.Country\n" +
                "FROM customers cus \n" +
                "INNER JOIN first_level_divisions d\n" +
                "ON cus.Division_ID = d.Division_ID\n" +
                "INNER JOIN countries cou\n" +
                "ON d.Country_ID = cou.Country_ID\n" +
                "WHERE cus.Customer_ID ="+id+" " +
                "LIMIT 1";
        Customer customer = new Customer();
        try(
                Statement statement = JDBC.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                int customer_id = resultSet.getInt("Customer_ID");
                String customer_name = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String division = resultSet.getString("Division");
                String phone = resultSet.getString("Phone");
                String country_name = resultSet.getString("Country");
                String postal_code = resultSet.getString("Postal_Code");
                customer.setCustomer_ID(customer_id);
                customer.setCustomer_Name(customer_name);
                customer.setAddress(address);
                customer.setDivision(division);
                customer.setCountry_name(country_name);
                customer.setPhone(phone);
                customer.setPostal_code(postal_code);
            }
        }catch(SQLException e){
            System.out.println("Error in findCustomer)"+ e.getMessage());
        }
        return  customer;
    }

    /**
     * Find a specific country by its name
     * @param name
     * @return country
     */
    @Override
    public Country findCountry(String name) {
        String sql = "SELECT * FROM countries WHERE Country = '"+name+"' LIMIT 1";
        Country country = new Country();
        try(
                Statement statement = JDBC.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                country.setCountry_ID(resultSet.getInt("Country_ID"));
                country.setCountryName(resultSet.getString("Country"));
            }
        }catch(SQLException e){
            System.out.println("Error in findCountry()"+ e.getMessage());
        }
        return country;
    }
}
