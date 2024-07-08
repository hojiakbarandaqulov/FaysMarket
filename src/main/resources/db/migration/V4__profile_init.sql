INSERT INTO profile (phone, password, status, visible, created_date, role)
VALUES ( '+9989157212135', '827ccbeea8a706c4c34a16891f84e7b', 'ACTIVE', true, now(),
        'ROLE_ADMIN') ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile;
