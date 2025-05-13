const fs = require('fs');
const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
});

class RentalManagement {
    constructor() {
        this.rentals = [];
        this.dataFilePath = 'rentals.json';
        this.loadData();  
    }


    loadData() {
        try {
            if (fs.existsSync(this.dataFilePath)) {
                const data = fs.readFileSync(this.dataFilePath, 'utf8');
                this.rentals = JSON.parse(data) || [];
            }
        } catch (err) {
            console.error('Error loading data:', err.message);
            this.rentals = [];
        }
    }


    saveData() {
        try {
            fs.writeFileSync(this.dataFilePath, JSON.stringify(this.rentals, null, 2));
        } catch (err) {
            console.error('Error saving data:', err.message);
        }
    }

    // Add new rental to system
    addRental(item, customer, startDate, endDate) {
        const rental = {
            id: this.rentals.length + 1,
            item,
            customer,
            start_date: startDate,
            end_date: endDate,
            status: 'active'
        };
        this.rentals.push(rental);  
        this.saveData();  
        console.log(`Rental #${rental.id} added successfully.`);
    }

    // List all rentals
    listRentals() {
        if (this.rentals.length === 0) {
            console.log('No rentals found.');
            return;
        }

        console.log('\nCurrent Rentals:');
        this.rentals.forEach(rental => {
            console.log(`ID: ${rental.id}, Item: ${rental.item}, Customer: ${rental.customer}, ` +
                `Period: ${rental.start_date} to ${rental.end_date}, Status: ${rental.status}`);
        });
    }

    // Update rental status
    updateRentalStatus(rentalId, newStatus) {
        const rental = this.rentals.find(r => r.id === rentalId);
        if (rental) {
            rental.status = newStatus; 
            this.saveData(); 
            console.log(`Rental #${rentalId} updated to '${newStatus}'.`);
        } else {
            console.log(`Rental #${rentalId} not found.`);
        }
    }

    // Display main menu
    displayMenu() {
        console.log('\nRental Management System');
        console.log('1. Add New Rental');
        console.log('2. List All Rentals');
        console.log('3. Update Rental Status');
        console.log('4. Exit');

        readline.question('Enter your choice: ', choice => {
            switch (choice) {
                case '1':
                    this.promptAddRental();
                    break;
                case '2':
                    this.listRentals();
                    this.displayMenu();
                    break;
                case '3':
                    this.promptUpdateStatus();
                    break;
                case '4':
                    console.log('Exiting system. Goodbye!');
                    readline.close();
                    break;
                default:
                    console.log('Invalid choice. Please try again.');
                    this.displayMenu();
            }
        });
    }

    // Helper method to prompt for rental details
    promptAddRental() {
        readline.question('Enter item name: ', item => {
            readline.question('Enter customer name: ', customer => {
                readline.question('Enter start date (YYYY-MM-DD): ', startDate => {
                    readline.question('Enter end date (YYYY-MM-DD): ', endDate => {
                        this.addRental(item, customer, startDate, endDate);
                        this.displayMenu();
                    });
                });
            });
        });
    }

    // Helper method to prompt for status update
    promptUpdateStatus() {
        readline.question('Enter rental ID to update: ', rentalId => {
            readline.question('Enter new status (active/completed/cancelled): ', newStatus => {
                this.updateRentalStatus(parseInt(rentalId), newStatus);
                this.displayMenu();
            });
        });
    }
}

// Main execution
const system = new RentalManagement();
system.displayMenu();