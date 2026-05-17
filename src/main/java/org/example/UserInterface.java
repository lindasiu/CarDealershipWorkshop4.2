package org.example;

import java.lang.invoke.VarHandle;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner;

    public UserInterface(){
    this.scanner = new Scanner(System.in);
    init();
    }
    private void init(){
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();

        if(this.dealership == null){
            System.out.println("ERROR: The dealership data file is missing");
            System.out.println("Please check if inventory.csv exists");
            System.out.println("The application cannot continue");
            System.exit(1);
        }
    }
    public void display(){
        init();
        boolean isRunning = true;
        while(isRunning){
            System.out.println("===== LINDA's CAR DELERSHIP =====");
            System.out.println("Please select from the following options:");
            System.out.println("1. Search vehicle by price");
            System.out.println("2. Search vehicle by make / model");
            System.out.println("3. Search vehicle by year");
            System.out.println("4. Search vehicle by color");
            System.out.println("5. Search vehicle by mileage");
            System.out.println("6. Search vehicle by type");
            System.out.println("7. View all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("10. Sell / Lease a Vehicle");
            System.out.println("0. EXIT");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByTypeRequest();
                    break;
                case 7:
                    processGetAllVehicleRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processNewContractRequest();
                    break;
                case 0:
                    System.out.println("Thank you for visiting. See you next time!");
                    System.out.println("BYE BYE!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error: Please enter a number between 1 - 10, or 0 to EXIT");
                    break;
            }
        }
    }
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found");
            return;
        }
        for (Vehicle v : vehicles) {
            System.out.printf("VIN: %d | %d %s %s | Type: %s | Color: %s | Odometer: %d | Price: $%.2f%n",
                    v.getVin(), v.getYear(), v.getMake(), v.getModel(), v.getVehicleType(), v.getColor(),
                    v.getOdometer(), v.getPrice());

        }
    }
    public void processGetAllVehicleRequest(){
        System.out.println("\n===== Current Inventory =====");
        List<Vehicle> allVehicles = this.dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }
    public void processGetByPriceRequest(){
        System.out.println("\n===== Search Vehicles by Price Range =====");
        System.out.println("Enter minimum price: $");
        double minPrice = scanner.nextDouble();
        System.out.println("Enter maximum price: $");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();

        List<Vehicle> priceRange = this.dealership.getVehicleByPrice(minPrice, maxPrice);

        System.out.println("\n===== Search Result by Price Range =====");
        displayVehicles(priceRange);
    }
    public void processGetByMakeModelRequest(){
        System.out.println("\n===== Search Vehicles by Make and Model =====");
        System.out.println("Enter make (ex: Toyota, Honda): ");
        String makeInput = scanner.nextLine().trim();

        System.out.println("Enter model (ex: Supra, Civic): ");
        String modelInput = scanner.nextLine().trim();

        List<Vehicle> results = this.dealership.getVehicleByMakeModel(makeInput, modelInput);

        System.out.println("\n===== Search Results by Make / Model =====");
        displayVehicles(results);
    }
    public void processGetByYearRequest(){
        System.out.println("\n===== Search Vehicles by Year =====");
        System.out.println("Enter minimum year: ");
        int minYear = scanner.nextInt();
        System.out.println("Enter maximum year: ");
        int maxYear = scanner.nextInt();
        scanner.nextLine();

        List<Vehicle> searchYear = this.dealership.getVehicleByYear(minYear, maxYear);

        System.out.println("\n===== Search Results by Year =====");
        displayVehicles(searchYear);
    }
    public void processGetByColorRequest(){
        System.out.println("\n===== Search Vehicles by Color =====");
        System.out.println("Enter color (ex: black, white): ");
        String colorInput = scanner.nextLine().trim();

        List<Vehicle> colorResults = this.dealership.getVehicleByColor(colorInput);

        System.out.println("\n===== Search Results by Color");
        displayVehicles(colorResults);
    }
    public void processGetByMileageRequest(){
        System.out.println("\n===== Search Vehicles by Mileage =====");
        System.out.println("Enter minimum mileage: ");
        int minMile = scanner.nextInt();
        System.out.println("Enter maximum mileage: ");
        int maxMile = scanner.nextInt();
        scanner.nextLine();

        List <Vehicle> mileSearch = this.dealership.getVehicleByMileage(minMile, maxMile);
        System.out.println("\n===== Search Results by Mileage =====");
        displayVehicles(mileSearch);
    }
    public void processGetByTypeRequest(){
        System.out.println("\n===== Search Vehicles by Type");
        System.out.println("Enter Vehicle type (ex: Sedan, SUV, Truck");
        String typeinput = scanner.nextLine().trim();

        List<Vehicle> typeResults = this.dealership.getVehicleByType(typeinput);

        System.out.println("\n===== Search Results by Car Type =====");
        displayVehicles(typeResults);
    }
    public void processAddVehicleRequest(){
        System.out.println("\n===== Add a New Vehicle to Inventory =====");

        System.out.println("Enter Vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Make: ");
        String make = scanner.nextLine().trim();

        System.out.println("Enter Model: ");
        String model = scanner.nextLine().trim();

        System.out.println("Enter Vehicle Type: ");
        String type = scanner.nextLine().trim();

        System.out.println("Enter Color: ");
        String color = scanner.nextLine().trim();

        System.out.println("Enter Vehicle Mileage: ");
        int odometer = scanner.nextInt();

        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Vehicle newVehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);

        this.dealership.addVehicle(newVehicle);
        DealershipFileManager.saveDealership(this.dealership);

        System.out.println("\n===== Vehicle has been added! =====");
    }
    public void processRemoveVehicleRequest() {
        System.out.println("\n===== Remove a Vehicle from Inventory =====");

        System.out.println("Enter VIN number to remove the vehicle: ");
        int vinInput = scanner.nextInt();
        scanner.nextLine();

        Vehicle removeVehicle = null;
        for (Vehicle v : this.dealership.getAllVehicles()) {
            if (v.getVin() == vinInput) {
                removeVehicle = v;
                break;
            }
        }
        if (removeVehicle != null) {
            this.dealership.removeVehicle(removeVehicle);
            DealershipFileManager.saveDealership(this.dealership);

            System.out.println("\nVehicle with VIN number: " + vinInput + " has been removed!");
        } else {
            System.out.println("Vehicle not found");
        }
    }
        public void processNewContractRequest(){
            System.out.println("\n===== New Sale or Lease Contract =====");
            System.out.println("Enter VIN number: ");
            int vinNumber = scanner.nextInt();
            scanner.nextLine();

            Vehicle vehicle = null;
            for(Vehicle v : this.dealership.getAllVehicles()){
                if(v.getVin() == vinNumber){
                    vehicle = v;
                    break;
                }
            }
            if(vehicle == null){
                System.out.println("Error: Vehicle VIN number not found");
                return;
            }
            System.out.println("Enter Contract Date (YYYY/MM/DD): ");
            String date = scanner.nextLine().trim();

            System.out.println("Enter Customer Full Name: ");
            String customerName = scanner.nextLine().trim();

            System.out.println("Enter Customer Email Address: ");
            String customerEmail = scanner.nextLine().trim();

            System.out.println("SALE or LEASING?: ");
            String contractType = scanner.nextLine().trim().toUpperCase();

            Contract finalContract = null;

            if(contractType.equals("SALE")){
                System.out.println("Will the customer be financing? (YES/NO): ");
                String financeInput = scanner.nextLine().trim().toUpperCase();
                boolean yesFinance = financeInput.equals("YES");

                finalContract = new SalesContract(date, customerName, customerEmail, vehicle, yesFinance);
            }
            else if(contractType.equals("LEASE")){
                if(vehicle.getYear() < 2023){
                    System.out.println("Cannot lease to older models!");
                }
                finalContract = new LeaseContract(date, customerName, customerEmail, vehicle);
            }else{
                System.out.println("Invalid contract selection");
                return;
            }
            System.out.println("\n===== Contract Review =====");
            System.out.printf("Base Vehicle Price: $%.2f\n", vehicle.getPrice());
            System.out.printf("Calculated Out-The-Door Total: $%.2f\n", finalContract.getTotalPrice());
            System.out.printf("Calculated Expected Monthly Statement: $%.2f\n", finalContract.getMonthlyPayment());
            ContractFileManager.saveContract(finalContract);

            this.dealership.removeVehicle(vehicle);

            DealershipFileManager.saveDealership(this.dealership);
            System.out.println("Transaction has been completed!");
        }


}

