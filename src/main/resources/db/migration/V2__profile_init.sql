INSERT INTO profile (phone, password, role, status, visible, created_date)
VALUES ('+998992431068', '1234', 'ROLE_ADMIN', 'ACTIVE', true, now())
ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile;