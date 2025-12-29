
package com.stockportfoliomanagementsystem;

import java.sql.Connection;

public class PostgreSqlCon {
    public static Connection getConnection() {
        return MySqlCon.MysqlMethod();
    }
}
