INSERT INTO roles (id, name, description)
VALUES (1, 'Admin', 'Role Admin'),
       (2, 'Customer', 'Role Customer');

INSERT INTO users (id, full_name, email, phone_number, password, is_verified, role_id, status)
VALUES (1, 'Super Admin', 'test@dev.id', '0811112223333',
        '$2y$10$25BaW45.H4EINIdFq70CNO3SFlR19Vrs.7t6Towe.6XJq.49q97Zm', true, 1,
        true),
       (2, 'Customer', 'customer@dev.test', '081222333444555',
		'$2y$10$25BaW45.H4EINIdFq70CNO3SFlR19Vrs.7t6Towe.6XJq.49q97Zm', true, 2,
		true);