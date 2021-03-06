package c195.dao.interfaces;

import c195.model.Country;
import c195.model.Customer;
import c195.model.FLDivision;
import javafx.collections.ObservableList;

public interface CustomerDaoInterface {
    public ObservableList<Customer> getAllCustomer();
    public void deleteCustomer(int id);
    public void addCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public ObservableList<Country> getCountryList();
    public ObservableList<FLDivision> getFLDDivisionList();
    public Customer findCustomer(int id);
    public  Country findCountry(String name);
}
