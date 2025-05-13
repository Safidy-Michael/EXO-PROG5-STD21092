class Rental:
    def __init__(self, rental_id, item, customer, start_date, end_date, status='active'):
        self.id = rental_id
        self.item = item
        self.customer = customer
        self.start_date = start_date
        self.end_date = end_date
        self.status = status

    def to_dict(self):
        return {
            'id': self.id,
            'item': self.item,
            'customer': self.customer,
            'start_date': self.start_date,
            'end_date': self.end_date,
            'status': self.status
        }
