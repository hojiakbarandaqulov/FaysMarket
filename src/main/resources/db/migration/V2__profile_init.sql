INSERT INTO profile ( name, surname, phone, password, status, visible, created_date, role)
VALUES ('Adminjon', 'Adminov', '+998995092376', '81dc9bdb52d04dc2036dbd8313ed055', 'ACTIVE', true, now(),
        'ROLE_ADMIN') ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile;