package entity;

public class Customer {

    private int id;
    private String fullName;
    private String mobileNumber;
    private double balance;

    public Customer() {
    }

    public Customer(int id, String fullName, String mobileNumber, double balance) {
        this.id = id;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.balance = balance;
    }

    public boolean hasBalance(double amount) {
        return balance >= amount;
    }

    public void deductBalance(double amount) {
        if (hasBalance(amount)) {
            balance -= amount;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
