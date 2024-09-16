SELECT 'CREATE DATABASE appointmentsdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'appointmentsdb');
