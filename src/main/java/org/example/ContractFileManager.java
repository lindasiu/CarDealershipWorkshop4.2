package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {
    private static final String FILE_PATH = "src/main/resources/contracts.csv";

    public static void saveContract(Contract contract){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))){
           Vehicle v = contract.getVehicleSold();
           StringBuilder line = new StringBuilder();

           if(contract instanceof SalesContract){
               line.append("SALE|");
            }else if (contract instanceof LeaseContract){
               line.append("LEASE|");
            }
           line.append(String.format("%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|", contract.getDate(), contract.getCustomerName(),
                   contract.getCustomerEmail(), v.getVin(), v.getYear(), v.getMake(), v.getModel(), v.getVehicleType(), v.getColor(),
                    v.getOdometer(), v.getPrice()));
            if(contract instanceof SalesContract){
                SalesContract sales = (SalesContract) contract;
                line.append(String.format("%.2f|%.2f|%.2f|%.2f|%s|%.2f", sales.getTotalPrice() - v.getPrice() - 100 - (v.getPrice() <
                        10000 ? 295 : 495), v.getPrice() < 10000 ? 295 : 495, sales.getTotalPrice(), sales.isFinanced() ? "YES" : "NO",
                        sales.getMonthlyPayment()));
            }else if(contract instanceof LeaseContract){
                LeaseContract lease = (LeaseContract) contract;
                line.append(String.format("%.2f|%.2f|%.2f|%.2f", lease.getExpectedEndValue(), lease.getLeaseFee(), lease.getTotalPrice(), lease.getMonthlyPayment()));
            }
            writer.write(line.toString());
            writer.newLine();
            System.out.println("Contract has been undated!");
        }
        catch(IOException e){
            System.out.println("Error: Was not able to update contract");
        }
    }
}
