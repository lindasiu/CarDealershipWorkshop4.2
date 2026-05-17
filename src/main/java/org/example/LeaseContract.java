package org.example;

public class LeaseContract extends Contract{
    private double expectedEndValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double expectedEndValue, double leaseFee) {
        super(date, customerName, customerEmail, vehicleSold);
        double vehiclePrice = vehicleSold.getPrice();
        this.expectedEndValue = vehiclePrice * 0.5;
        this.leaseFee = vehiclePrice * 0.07;

    }
    public double getExpectedEndValue(){
        return expectedEndValue;
    }
    public double getLeaseFee(){
        return leaseFee;
    }

    @Override
    public double getTotalPrice(){
        return getVehicleSold().getPrice() + this.leaseFee;

    }
    @Override
    public double getMonthlyPayment(){
        int months = 36;
        double interestRate = 0.04;
        double monthlyRate = interestRate / 12;
        double principal = getTotalPrice();
        double monthlyPayment = (monthlyRate * principal) / (1 - Math.pow(1 + monthlyRate, - months));
        return monthlyPayment;
    }
}
