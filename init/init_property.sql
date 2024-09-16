SELECT 'CREATE DATABASE propertiesdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'propertiesdb');
