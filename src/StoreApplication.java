import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;

public class StoreApplication {
    static final String DRIVER = "oracle.jdbc.OracleDriver";
    static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    static final String USERNAME = "COMP228_F22_ya_30";
    static final String PASSWORD = "password";
    static Connection con;
    static HashMap<Integer, Customer> customerMap;
    static HashMap<Integer, Fruit> fruitMap;
    static HashMap<Integer, Orders> ordersMap;

    public static void main(String args[]) throws Exception {

        customerMap = new HashMap<Integer, Customer>();
        fruitMap = new HashMap<Integer, Fruit>();
        ordersMap = new HashMap<Integer, Orders>();

        // Database authentication
        authenticate();

        // Populate each HashMap
        populateCustomer();
        populateFruit();
        populateOrders();

        //Iterate over ordersMap & print customerMap and fruitMap using the common method
        Iterator<Integer> iteratorOrders = ordersMap.keySet().iterator();
        while(iteratorOrders.hasNext()) {
            Orders orders = ordersMap.get(iteratorOrders.next());
            System.out.println("CustomerOrderId: " + orders.customerOrderId);
            printDetails(customerMap, orders.customerId);
            printDetails(fruitMap, orders.fruitId);
            System.out.println("Payment Type: " + orders.paymentType);
            System.out.println("");
        }

        // Print all orders in the same way
        for (Integer i : ordersMap.keySet()){
            printDetails(ordersMap, i);
        }
    }

    public static void authenticate() throws ClassNotFoundException, SQLException {
        try{
            System.out.println(">> Starting Program!");
            Class.forName(DRIVER);
            System.out.println(">> Driver Loaded Successfully!");
            con = DriverManager.getConnection(DATABASE_URL,USERNAME, PASSWORD);
            System.out.println(">> Database Connected Successfully!");
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populateCustomer() {
        String sqlCustomer = "SELECT * FROM CUSTOMER";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sqlCustomer);
            while (rs.next()) {
                int customerId = rs.getInt("CUSTOMER_ID");
                String customerName = rs.getString("CUSTOMER_NAME");
                String address = rs.getString("ADDRESS");
                String email = rs.getString("EMAIL");
                String password = rs.getString("PASSWORD");
                String phoneNumber = rs.getString("PHONE_NUMBER");
                Customer customer = new Customer(customerId, customerName, address, email, password, phoneNumber);
                customerMap.put(customerId, customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populateFruit() {
        String sqlFruit = "SELECT * FROM FRUIT";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sqlFruit);
            while (rs.next()) {
                int fruitId = rs.getInt("FRUIT_ID");
                String fruitName = rs.getString("FRUIT_NAME");
                int price = rs.getInt("PRICE");
                String description = rs.getString("DESCRIPTION");
                String origin = rs.getString("ORIGIN");
                Fruit fruit = new Fruit(fruitId, fruitName, price, description, origin);
                fruitMap.put(fruitId, fruit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populateOrders() {
        String sqlOrders = "SELECT * FROM ORDERS";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sqlOrders);
            while (rs.next()) {
                int customerOrderId = rs.getInt("CUSTOMER_ORDER_ID");
                int customerId = rs.getInt("CUSTOMER_ID");
                int fruitId = rs.getInt("FRUIT_ID");
                String paymentType = rs.getString("PAYMENT_TYPE");
                Orders orders = new Orders(customerOrderId, customerId, fruitId, paymentType);
                ordersMap.put(customerOrderId, orders);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printDetails(HashMap hashMap, int id) {
        if (hashMap == customerMap) {
            Customer cus = customerMap.get(id);
            System.out.println("----------------------------------------");
            System.out.println("CustomerId: " + cus.customerId + "\nCustomerName: " + cus.customerName + "\nCustomerAddress: " + cus.address +
                    "\nCustomerEmail: " + cus.email + "\nCustomerPhoneNumber: " + cus.phoneNumber);
        } else if (hashMap == fruitMap) {
            Fruit fru = fruitMap.get(id);
            System.out.println("----------------------------------------");
            System.out.println("FruitId: " + fru.fruitId + "\nFruitName: " + fru.fruitName + "\nPrice: " + fru.price + "$\nDescription: " + fru.description +
                    "\nOrigin: " + fru.origin);
            System.out.println("----------------------------------------");
        } else if (hashMap == ordersMap) {
            Orders ord = ordersMap.get(id);
            System.out.println("*************Order Details*************");
            System.out.println("CustomerOrderId: "+ord.customerOrderId+"\nCustomerId: "+ord.customerId+"\nFruitId: "+ord.fruitId+
                    "\nPayment Type: "+ord.paymentType);
            System.out.println("");
        } else {
            System.out.println("Invalid Command!");
        }
    }
}
