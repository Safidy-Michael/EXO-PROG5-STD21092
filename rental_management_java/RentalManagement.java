import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class RentalManagement {
    private List<Map<String, Object>> rentals;
    private JSONParser parser;

    // Initialize rental management system
    public RentalManagement() {
        rentals = new ArrayList<>();
        parser = new JSONParser();
        loadData();
    }

    // Load data from JSON file
    @SuppressWarnings("unchecked")
    private void loadData() {
        try (FileReader reader = new FileReader("rentals.json")) {
            JSONArray rentalArray = (JSONArray) parser.parse(reader);
            rentals = new ArrayList<>(rentalArray);
        } catch (FileNotFoundException e) {
            rentals = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            rentals = new ArrayList<>();
        }
    }

    // Save data to JSON file
    private void saveData() {
        try (FileWriter file = new FileWriter("rentals.json")) {
            file.write(new JSONArray(rentals).toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Add new rental to system
    public void addRental(String item, String customer, String startDate, String endDate) {
        Map<String, Object> rental = new HashMap<>();
        rental.put("id", rentals.size() + 1);
        rental.put("item", item);
        rental.put("customer", customer);
        rental.put("start_date", startDate);
        rental.put("end_date", endDate);
        rental.put("status", "active");
        rentals.add(rental);  // Add to rentals list
        saveData();  // Persist changes
        System.out.printf("Rental #%d added successfully.%n", rentals.size());
    }

    // List all rentals
    public void listRentals() {
        if (rentals.isEmpty()) {
            System.out.println("No rentals found.");
            return;
        }

        System.out.println("\nCurrent Rentals:");
        for (Map<String, Object> rental : rentals) {
            System.out.printf("ID: %d, Item: %s, Customer: %s, Period: %s to %s, Status: %s%n",
                    rental.get("id"), rental.get("item"), rental.get("customer"),
                    rental.get("start_date"), rental.get("end_date"), rental.get("status"));
        }
    }

    // Update rental status
    public void updateRentalStatus(int rentalId, String newStatus) {
        for (Map<String, Object> rental : rentals) {
            if ((long) rental.get("id") == rentalId) {
                rental.put("status", newStatus);  // Update status
                saveData();  // Persist changes
                System.out.printf("Rental #%d updated to '%s'.%n", rentalId, newStatus);
                return;
            }
        }
        System.out.printf("Rental #%d not found.%n", rentalId);
    }

    // Display main menu
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nRental Management System");
            System.out.println("1. Add New Rental");
            System.out.println("2. List All Rentals");
            System.out.println("3. Update Rental Status");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter item name: ");
                    String item = scanner.nextLine();
                    System.out.print("Enter customer name: ");
                    String customer = scanner.nextLine();
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    addRental(item, customer, startDate, endDate);
                    break;
                case "2":
                    listRentals();
                    break;
                case "3":
                    System.out.print("Enter rental ID to update: ");
                    int rentalId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new status (active/completed/cancelled): ");
                    String newStatus = scanner.nextLine();
                    updateRentalStatus(rentalId, newStatus);
                    break;
                case "4":
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        RentalManagement system = new RentalManagement();
        system.displayMenu();
    }
}