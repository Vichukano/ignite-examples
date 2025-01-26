CREATE TABLE IF NOT EXISTS Customer(
id long PRIMARY KEY,
name varchar NOT NULL,
) with "CACHE_NAME=person-cache3,VALUE_TYPE=ru.vichukano.ignite_examples.module.Customer";
