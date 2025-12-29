-- 1. Таблица поставщиков
CREATE TABLE IF NOT EXISTS supplier (
                                        S_ID VARCHAR(10) NOT NULL PRIMARY KEY,
    S_Name VARCHAR(100),
    S_Contact VARCHAR(20),
    Description VARCHAR(255),
    S_Location VARCHAR(100)
    );

-- 2. Таблица клиентов
CREATE TABLE IF NOT EXISTS customer (
                                        C_ID VARCHAR(10) NOT NULL PRIMARY KEY,
    C_Name VARCHAR(100),
    C_Location VARCHAR(100),
    C_Contact VARCHAR(20)
    );

-- 3. Таблица товаров (Stock)
CREATE TABLE IF NOT EXISTS stock (
                                     P_ID VARCHAR(10) NOT NULL PRIMARY KEY,
    P_Name VARCHAR(100) NOT NULL,
    Price_taken DECIMAL(10,2),
    Selling_price DECIMAL(10,2),
    Qty INT,
    P_Description VARCHAR(255),
    S_ID VARCHAR(10) REFERENCES supplier (S_ID),
    Total DECIMAL(15,2)
    );

-- 4. Таблица пользователей
CREATE TABLE IF NOT EXISTS users (
                                     User_Id VARCHAR(10) NOT NULL PRIMARY KEY,
    Username VARCHAR(100) NOT NULL,
    Password VARCHAR(100),
    FName VARCHAR(50),
    Lname VARCHAR(50),
    NIC VARCHAR(20),
    Position VARCHAR(50),
    Contact VARCHAR(20),
    Pic BYTEA -- В PostgreSQL вместо longblob используется BYTEA
    );

-- 5. Таблицы транзакций
CREATE TABLE IF NOT EXISTS transactions_cus (
                                                transaction_id VARCHAR(10) PRIMARY KEY,
    Date_ DATE,
    Description VARCHAR(255),
    Qty INT,
    Price DECIMAL(10,2),
    Total DECIMAL(15,2),
    C_ID VARCHAR(10) REFERENCES customer (C_ID),
    P_ID VARCHAR(10) REFERENCES stock (P_ID)
    );

CREATE TABLE IF NOT EXISTS temp_invoice (
                                            transaction_id VARCHAR(10) PRIMARY KEY,
    Date_ DATE,
    Description VARCHAR(255),
    Qty INT,
    Price DECIMAL(10,2),
    Total DECIMAL(15,2),
    C_ID VARCHAR(10) REFERENCES customer (C_ID),
    P_ID VARCHAR(10) REFERENCES stock (P_ID)
    );

-- 6. Заполнение тестовыми данными (ВАЖНО для работы Java-кода)
-- Добавляем поставщиков
INSERT INTO supplier (S_ID, S_Name, S_Contact, Description, S_Location) VALUES
                                                                            ('S001', 'Sammanee', '076-3963385', 'books', 'Colombo'),
                                                                            ('S002', 'Atlas', '071-4563218', 'water bottle', 'Rathnapura');

-- Добавляем товары (ID должны быть P001, P002 и т.д., чтобы Java их видела)
INSERT INTO stock (P_ID, P_Name, Price_taken, Selling_price, Qty, S_ID, Total) VALUES
                                                                                   ('P001', 'Blue Pens', 35.00, 40.00, 200, 'S001', 8000.00),
                                                                                   ('P002', 'Pencils', 16.00, 20.00, 400, 'S002', 8000.00);

-- Добавляем пользователя для входа
INSERT INTO users (User_Id, Username, Password, Position) VALUES
    ('U001', 'admin', 'admin123', 'Stock keeper');