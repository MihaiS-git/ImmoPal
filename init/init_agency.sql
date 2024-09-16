SELECT 'CREATE DATABASE agenciesdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'agenciesdb');
