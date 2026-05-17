package org.example;

public class SalesContract extends Contract{
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double salesTaxAmount, double recordingFee, double processingFee, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicleSold);
        this.isFinanced = isFinanced;
        double vehiclePrice = vehicleSold.getPrice();
        this.salesTaxAmount = vehiclePrice * 0.05;
        this.recordingFee = 100;

        if(vehiclePrice < 10000){
            this.processingFee = 295;
        }else {
            this.processingFee = 495;
        }
        }
    public boolean isFinanced(){
        return isFinanced;
    }
    @Override
    public double getTotalPrice(){
        return getVehicleSold().getPrice() + this.salesTaxAmount + this.recordingFee + this.processingFee;
    }
    @Override
    public double getMonthlyPayment(){
        if (!isFinanced){
            return 0.0;
        }
        double vehiclePrice = getVehicleSold().getPrice();
        double interestRate;
        int months;

        if(vehiclePrice >= 10000){
            interestRate = 0.0425;
            months = 48;
        }else{
            interestRate = 0.0525;
            months = 24;
        }
        double monthlyRate = interestRate /12;
        double principal = getTotalPrice();

        return (monthlyRate * principal) / (1 - Math.pow(1 + monthlyRate, - months));
    }
}
