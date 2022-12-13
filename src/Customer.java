public class Customer {
    int customerId;
    String customerName, address, email, password, phoneNumber;
    public Customer(int customerId, String customerName, String address, String email, String password, String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
