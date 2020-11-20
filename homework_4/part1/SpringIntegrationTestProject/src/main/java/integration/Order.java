package integration;

public class Order {
    private int id;
    private int sum;
    private String state;

    public Order() {
    }

    public Order(int id, int sum, String state) {
        this.id = id;
        this.sum = sum;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", sum=" + sum +
                ", state='" + state + '\'' +
                '}';
    }


}
