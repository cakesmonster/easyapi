CREATE TABLE employees
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    salary DOUBLE,
    hire_date     DATE,
    department_id BIGINT
);


INSERT INTO employees (first_name, last_name, salary, hire_date, department_id)
VALUES ('John', 'Doe', 50000.0, '2024-01-01', 101);

INSERT INTO employees (first_name, last_name, salary, hire_date, department_id)
VALUES ('Jane', 'Smith', 60000.0, '2023-05-15', 102);

INSERT INTO employees (first_name, last_name, salary, hire_date, department_id)
VALUES ('Mike', 'Johnson', 55000.0, '2024-02-28', 101);

INSERT INTO employees (first_name, last_name, salary, hire_date, department_id)
VALUES ('Sarah', 'Williams', 62000.0, '2023-08-01', 103);
