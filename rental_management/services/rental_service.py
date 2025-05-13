import json
from models.rental import Rental

class RentalService:
    def __init__(self):
        self.rentals = []
        self.load_data()

    def load_data(self):
        try:
            with open('data/rentals.json', 'r') as file:
                self.rentals = json.load(file)
        except (FileNotFoundError, json.JSONDecodeError):
            self.rentals = []

    def save_data(self):
        with open('data/rentals.json', 'w') as file:
            json.dump(self.rentals, file, indent=4)

    def add_rental(self, item, customer, start_date, end_date):
        rental = Rental(len(self.rentals) + 1, item, customer, start_date, end_date)
        self.rentals.append(rental.to_dict())
        self.save_data()
        print(f"Rental #{rental.id} added successfully.")

    def list_rentals(self):
        if not self.rentals:
            print("No rentals found.")
            return
        
        print("\nCurrent Rentals:")
        for rental in self.rentals:
            print(f"ID: {rental['id']}, Item: {rental['item']}, Customer: {rental['customer']}, "
                  f"Period: {rental['start_date']} to {rental['end_date']}, Status: {rental['status']}")

    def update_rental_status(self, rental_id, new_status):
        for rental in self.rentals:
            if rental['id'] == rental_id:
                rental['status'] = new_status
                self.save_data()
                print(f"Rental #{rental_id} updated to '{new_status}'.")
                return
        print(f"Rental #{rental_id} not found.")
