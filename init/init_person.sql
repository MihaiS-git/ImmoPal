SELECT 'CREATE DATABASE personsdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'personsdb');
