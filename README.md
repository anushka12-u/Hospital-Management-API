# Hospital-Management-API
A simple Hospital Management System API built using Java, JDBC, and MySQL.
This project manages patients and doctors data with basic CRUD operations.

It is designed as a backend learning project to understand database connectivity, API logic, and data management.

**Features**

Add new patients

View patient records

Add doctors with specialization

View doctor list

Store data using MySQL database

JDBC-based database connectivity

**Create Database**
      CREATE DATABASE hospital;
      USE hospital;
**Create Patient Table**
        CREATE TABLE patient(
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(220),
    age INT,
    gender VARCHAR(15)
);

**Create doctor's table**
     CREATE TABLE doctors(
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(400),
    specialization VARCHAR(220)
);

