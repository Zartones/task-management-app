INSERT INTO users (id, email, password, first_name, last_name, username)
VALUES (1, 'test@gmail.com', 'password', 'Name', 'Last', 'Uname');

INSERT INTO projects (id, name, description, start_date, end_date, status, user_id)
VALUES (1, 'Test Project', 'Test Description', '2025-01-01', '2025-06-01', 'INITIATED', 1);
