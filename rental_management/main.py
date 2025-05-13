from services.rental_service import RentalService

def display_menu():
    system = RentalService()

    while True:
        print("\nRental Management System")
        print("1. Add New Rental")
        print("2. List All Rentals")
        print("3. Update Rental Status")
        print("4. Exit")

        choice = input("Enter your choice: ")

        if choice == '1':
            item = input("Enter item name: ")
            customer = input("Enter customer name: ")
            start_date = input("Enter start date (YYYY-MM-DD): ")
            end_date = input("Enter end date (YYYY-MM-DD): ")
            system.add_rental(item, customer, start_date, end_date)
        elif choice == '2':
            system.list_rentals()
        elif choice == '3':
            rental_id = int(input("Enter rental ID to update: "))
            new_status = input("Enter new status (active/completed/cancelled): ")
            system.update_rental_status(rental_id, new_status)
        elif choice == '4':
            print("Exiting system. Goodbye!")
            break
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    display_menu()
