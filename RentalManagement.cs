using System;
using System.Collections.Generic;
using System.IO;
using Newtonsoft.Json;

class RentalManagement
{
    private List<Dictionary<string, object>> rentals;
    private string dataFilePath = "rentals.json";
    
    // Initialize rental management system
    public RentalManagement()
    {
        rentals = new List<Dictionary<string, object>>();
        LoadData();
    }
    

    private void LoadData()
    {
        try
        {
            if (File.Exists(dataFilePath))
            {
                string json = File.ReadAllText(dataFilePath);
                rentals = JsonConvert.DeserializeObject<List<Dictionary<string, object>>>(json) 
                    ?? new List<Dictionary<string, object>>();
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error loading data: {ex.Message}");
            rentals = new List<Dictionary<string, object>>();
        }
    }
    

    private void SaveData()
    {
        try
        {
            string json = JsonConvert.SerializeObject(rentals, Formatting.Indented);
            File.WriteAllText(dataFilePath, json);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error saving data: {ex.Message}");
        }
    }
    
   
    public void AddRental(string item, string customer, string startDate, string endDate)
    {
        var rental = new Dictionary<string, object>
        {
            {"id", rentals.Count + 1},
            {"item", item},
            {"customer", customer},
            {"start_date", startDate},
            {"end_date", endDate},
            {"status", "active"}
        };
        rentals.Add(rental);  
        SaveData();  
        Console.WriteLine($"Rental #{rentals.Count} added successfully.");
    }
    
    // List all rentals
    public void ListRentals()
    {
        if (rentals.Count == 0)
        {
            Console.WriteLine("No rentals found.");
            return;
        }
        
        Console.WriteLine("\nCurrent Rentals:");
        foreach (var rental in rentals)
        {
            Console.WriteLine($"ID: {rental["id"]}, Item: {rental["item"]}, Customer: {rental["customer"]}, " +
                $"Period: {rental["start_date"]} to {rental["end_date"]}, Status: {rental["status"]}");
        }
    }
    
    // Update rental status
    public void UpdateRentalStatus(int rentalId, string newStatus)
    {
        foreach (var rental in rentals)
        {
            if (Convert.ToInt32(rental["id"]) == rentalId)
            {
                rental["status"] = newStatus; 
                SaveData();  
                Console.WriteLine($"Rental #{rentalId} updated to '{newStatus}'.");
                return;
            }
        }
        Console.WriteLine($"Rental #{rentalId} not found.");
    }
    
    // Display main menu
    public void DisplayMenu()
    {
        while (true)
        {
            Console.WriteLine("\nRental Management System");
            Console.WriteLine("1. Add New Rental");
            Console.WriteLine("2. List All Rentals");
            Console.WriteLine("3. Update Rental Status");
            Console.WriteLine("4. Exit");
            
            Console.Write("Enter your choice: ");
            string choice = Console.ReadLine();
            
            switch (choice)
            {
                case "1":
                    Console.Write("Enter item name: ");
                    string item = Console.ReadLine();
                    Console.Write("Enter customer name: ");
                    string customer = Console.ReadLine();
                    Console.Write("Enter start date (YYYY-MM-DD): ");
                    string startDate = Console.ReadLine();
                    Console.Write("Enter end date (YYYY-MM-DD): ");
                    string endDate = Console.ReadLine();
                    AddRental(item, customer, startDate, endDate);
                    break;
                case "2":
                    ListRentals();
                    break;
                case "3":
                    Console.Write("Enter rental ID to update: ");
                    int rentalId = int.Parse(Console.ReadLine());
                    Console.Write("Enter new status (active/completed/cancelled): ");
                    string newStatus = Console.ReadLine();
                    UpdateRentalStatus(rentalId, newStatus);
                    break;
                case "4":
                    Console.WriteLine("Exiting system. Goodbye!");
                    return;
                default:
                    Console.WriteLine("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    // Main method
    static void Main(string[] args)
    {
        RentalManagement system = new RentalManagement();
        system.DisplayMenu();
    }
}