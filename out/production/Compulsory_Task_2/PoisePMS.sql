--Holds a List of Projects
CREATE TABLE Projects (
  Project_Number char(10) PRIMARY KEY,
  Name varchar(255) NOT NULL,
  Building_Type int NOT NULL,
  ERF_Number int NOT NULL,
  Address int NOT NULL,
  Total_Price double NOT NULL,
  Total_Paid double NOT NULL,
  Project_Manager int NOT NULL,
  Structural_Engineer int NOT NULL,
  Architect int NOT NULL,
  Contractor int NOT NULL,
  Customer int NOT NULL,
  Deadline date NOT NULL,
  Start_Date date,
  Completion_Date date
);

--Holds a List of Project Managers
CREATE TABLE Project_Managers (
  id int PRIMARY KEY AUTO_INCREMENT,
  Personal_Details int NOT NULL
);

--Holds a List of Structural Engineers
CREATE TABLE Structural_Engineers (
  id int PRIMARY KEY AUTO_INCREMENT,
  Personal_Details int NOT NULL
);

--Holds a List of Architects
CREATE TABLE Architects (
  id int PRIMARY KEY AUTO_INCREMENT,
  Personal_Details int NOT NULL
);

--Holds a List of Contractors
CREATE TABLE Contractors (
  id int PRIMARY KEY AUTO_INCREMENT,
  Personal_Details int NOT NULL
);

--Holds a List of Customers
CREATE TABLE Customers (
  id int PRIMARY KEY AUTO_INCREMENT,
  Personal_Details int NOT NULL
);

--This will hold all the Personal Details
CREATE TABLE Personal_Details (
  id int PRIMARY KEY AUTO_INCREMENT,
  Name varchar(255) NOT NULL,
  Phone_Number varchar(255) NOT NULL,
  Email varchar(255) NOT NULL,
  Address int NOT NULL
);

--This will hold all the Addresses
CREATE TABLE Addresses (
  id int PRIMARY KEY AUTO_INCREMENT,
  Street_Number int NOT NULL,
  Street_Name varchar(255) NOT NULL,
  City varchar(255) NOT NULL,
  Region varchar(255) NOT NULL,
  Postal_Code int(4) NOT NULL
);

--This will hold the Type of the Buildings (Apartment, House, Store, etc...)
CREATE TABLE Buildings (
  id int PRIMARY KEY AUTO_INCREMENT,
  Building_Type varchar(255) NOT NULL
);

--Project References
ALTER TABLE Projects ADD FOREIGN KEY (Building_Type) REFERENCES Buildings (id);
ALTER TABLE Projects ADD FOREIGN KEY (Address) REFERENCES Addresses (id);
ALTER TABLE Projects ADD FOREIGN KEY (Project_Manager) REFERENCES Project_Managers (id);
ALTER TABLE Projects ADD FOREIGN KEY (Structural_Engineer) REFERENCES Structural_Engineers (id);
ALTER TABLE Projects ADD FOREIGN KEY (Architect) REFERENCES Architects (id);
ALTER TABLE Projects ADD FOREIGN KEY (Contractor) REFERENCES Contractors (id);
ALTER TABLE Projects ADD FOREIGN KEY (Customer) REFERENCES Customers (id);

--Personal_Details References
ALTER TABLE Personal_Details ADD FOREIGN KEY (Address) REFERENCES Addresses (id);
ALTER TABLE Project_Managers ADD FOREIGN KEY (Personal_Details) REFERENCES Personal_Details (id);
ALTER TABLE Structural_Engineers ADD FOREIGN KEY (Personal_Details) REFERENCES Personal_Details (id);
ALTER TABLE Architects ADD FOREIGN KEY (Personal_Details) REFERENCES Personal_Details (id);
ALTER TABLE Contractors ADD FOREIGN KEY (Personal_Details) REFERENCES Personal_Details (id);
ALTER TABLE Customers ADD FOREIGN KEY (Personal_Details) REFERENCES Personal_Details (id);