package org.example;

import java.io.*;
import java.nio.Buffer;
import java.util.Scanner;

public class DealershipFileManager {
    private static final String FILE_PATH = "src/main/resources/inventory.csv";

    public static Dealership getDealership(){
        Dealership dealership = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            if((line = reader.readLine()) != null){
                String[] dealershipData = line.split("\\|");
                String name = dealershipData[0];
                String address = dealershipData[1];
                String phone = dealershipData[2];

                dealership = new Dealership(name, address, phone);
            }
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                String[] csvRow = line.split("\\|");
                if(csvRow.length < 8){
                    continue;
                }

                int vin = Integer.parseInt(csvRow[0].trim());
                int year = Integer.parseInt(csvRow[1].trim());
                String make = csvRow[2].trim();
                String model = csvRow[3].trim();
                String vehicleType = (csvRow[4].trim().toUpperCase());
                String color = csvRow[5].trim();
                int odometer = Integer.parseInt(csvRow[6].trim());
                double price = Double.parseDouble(csvRow[7].trim());

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                dealership.addVehicle(vehicle);
            }
            reader.close();
        }
        catch (IOException e) {
            System.out.println("Error: Could not find or open the file.");
        }
        catch (Exception e) {
            System.out.println("Error: Something went wrong within the file.");
        }
        return dealership;
    }
        public static void saveDealership(Dealership dealership){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            String headerLine = String.format("%s|%s|%s\n", dealership.getName(), dealership.getAddress(),
                    dealership.getPhone());
            writer.write(headerLine);

            for(Vehicle v : dealership.getAllVehicles()){
                String vehicleLine = String.format("%d|%d|%s|%s|%s|%s|%d|%.2f\n", v.getVin(), v.getYear(), v.getMake(),
                        v.getModel(), v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
                writer.write(vehicleLine);
            }

        } catch (Exception e) {
            System.out.println("Error saving dealer data.");
        }
    }
}
