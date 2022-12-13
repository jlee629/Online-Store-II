public class Orders {
    int customerOrderId, customerId, fruitId;
    String paymentType;
    public Orders(int customerOrderId, int customerId, int fruitId, String paymentType) {
        this.customerOrderId = customerOrderId;
        this.customerId = customerId;
        this.fruitId = fruitId;
        this.paymentType = paymentType;
    }
}
