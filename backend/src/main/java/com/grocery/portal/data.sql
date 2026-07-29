INSERT INTO product (name, description, price, image, quantity) VALUES
                                                                    ('Bananas', 'Fresh yellow bananas, sold per bunch', 1.29, 'bananas.jpg', 100),
                                                                    ('Whole Milk', 'Whole milk, 1 gallon', 3.49, 'milk.jpg', 50),
                                                                    ('Eggs', 'Large grade A eggs, dozen', 2.99, 'eggs.jpg', 60),
                                                                    ('Bread', 'Whole wheat sandwich bread', 2.49, 'bread.jpg', 40),
                                                                    ('Chicken Breast', 'Boneless skinless chicken breast, per lb', 4.99, 'chicken.jpg', 30),
                                                                    ('Apples', 'Honeycrisp apples, per lb', 1.99, 'apples.jpg', 80),
                                                                    ('Rice', 'Long grain white rice, 5 lb bag', 6.49, 'rice.jpg', 25),
                                                                    ('Coffee', 'Ground medium roast coffee, 12 oz', 8.99, 'coffee.jpg', 35);

INSERT INTO discount_code (code, percentage, active) VALUES
                                                         ('SAVE10', 0.10, true),
                                                         ('SAVE20', 0.20, true);