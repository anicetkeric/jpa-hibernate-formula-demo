INSERT INTO public.users (first_name, last_name, birth_date, salary) VALUES
('Alice', 'Smith', '1990-05-20', 60000.00),
('Bob', 'Johnson', '1985-11-10', 75000.00),
('Carol', 'Williams', '2000-02-28', 50000.00),
('David', 'Brown', '1975-08-15', 95000.00),
('Eve', 'Davis', '1995-12-01', 70000.00);


-- Insert sample order
INSERT INTO public.orders (order_date) VALUES ('2023-09-01 10:30:00');

-- Insert items for order ID 1
INSERT INTO public.order_items (order_id, quantity, price) VALUES
(1, 2, 25.50),
(1, 1, 100.00),
(1, 3, 10.00);

