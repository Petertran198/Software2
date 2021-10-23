package c195.dao.interfaces;

import c195.model.Country;
import c195.model.Customer;
import javafx.collections.ObservableList;

public interface CustomerDaoInterface {
    public ObservableList<Customer> getAllCustomer();
    public void deleteCustomer(int id);
    public void addCustomer(Customer customer);
    public ObservableList<Country> getCountryList();
}
